package com.mystery.chat.managers;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.configures.AppConfig;
import com.mystery.chat.services.MemberService;
import com.mystery.chat.services.UserService;
import com.mystery.chat.utils.RWList;
import com.mystery.chat.utils.RWMap;
import com.mystery.chat.vos.MessageVO;
import com.mystery.chat.vos.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户WebSocket会话管理器
 *
 * @author shouchen
 * @date 2022/11/24
 */
@Component
public class ClientWebSocketSessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientWebSocketSessionManager.class);
    private final RWMap<String, WebSocketSession> sessionMap;
    private final RWMap<String, RWList<WebSocketSession>> roomSessionMap;
    private final ScheduledThreadPoolExecutor timerExecutor;
    private final ThreadPoolExecutor executor;
    private UserService userService;
    private MemberService memberService;

    public ClientWebSocketSessionManager(AppConfig appConfig) {
        sessionMap = new RWMap<>();
        roomSessionMap = new RWMap<>();
        timerExecutor = new ScheduledThreadPoolExecutor(1);
        executor = new ThreadPoolExecutor(appConfig.broadcastThreadPollSize,
                appConfig.broadcastThreadPollSize,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(appConfig.broadcastThreadPollSize * 32),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public void putSession(String uid, WebSocketSession session) throws Exception {
        synchronized (sessionMap) {
            if (sessionMap.containsKey(uid)) {
                // 该用户已连接，则断开该连接，禁止二次登陆
                executor.execute(() -> {
                    try {
                        session.sendMessage(new TextMessage(
                                JSON.toJSONString(ResultVO.error("Can not re-login"))
                        ));
                        session.close(CloseStatus.NOT_ACCEPTABLE);
                    } catch (IOException e) {
                        LOGGER.warn("", e);
                    }
                });
                return;
            } else {
                // 正常连接
                sessionMap.put(uid, session);
                // 心跳检测 和 超时检测
                heartTest(session);
            }
        }
        roomSessionMap.forEach((roomID, rwList) -> {
            if (memberService.userIsInRoom(uid, roomID)) {
                rwList.add(session);
            }
        });
        LOGGER.info("{}:{} login", uid, userService.getByUID(uid)
                .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                .orElse("???"));
    }

    private void heartTest(WebSocketSession session) {
        timerExecutor.schedule(() -> {
            try {
                long now = System.currentTimeMillis();
                long expire = (long) session.getAttributes().get("expire");
                long heartBeat = (long) session.getAttributes().get("heart-beat");
                if (now > expire || now > heartBeat + TimeUnit.SECONDS.toMillis(24)) {
                    session.close();
                    return;
                }
                session.sendMessage(new PingMessage());
                heartTest(session);
            } catch (IOException e) {
                LOGGER.warn("", e);
            }
        }, 10, TimeUnit.SECONDS);
    }

    public void removeSession(String uid) {
        WebSocketSession session = sessionMap.remove(uid);
        roomSessionMap.forEach((roomID, rwList) -> rwList.remove(session));
        LOGGER.info("{}:{} logout", uid, userService.getByUID(uid)
                .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                .orElse("???"));
    }

    public Optional<WebSocketSession> getSession(String uid) {
        return Optional.ofNullable(sessionMap.get(uid));
    }

    public boolean contents(String uid) {
        return sessionMap.containsKey(uid);
    }

    private void broadcastMsg(String msg) {
        sessionMap.forEach((k, v) -> executor.execute(() -> {
            try {
                v.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                LOGGER.warn("", e);
            }
        }));
    }

    public void broadcastMsg(MessageVO messageVO) {
        Objects.requireNonNull(messageVO);
        TextMessage msg = new TextMessage(JSON.toJSONString(
                ResultVO.of(messageVO).setType("MSG")
        ));
        executor.execute(() -> roomSessionMap
                .computeIfAbsent(messageVO.getRoomID(),
                        roomID -> {
                            RWList<WebSocketSession> list = new RWList<>();
                            sessionMap.forEach((uid, session) -> {
                                if (memberService.userIsInRoom(uid, roomID)) {
                                    list.add(session);
                                }
                            });
                            return list;
                        })
                .forEach(session -> executor.execute(() -> {
                    try {
                        session.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }))
        );
    }

    @Autowired
    public ClientWebSocketSessionManager setUserService(@Lazy UserService userService) {
        this.userService = userService;
        return this;
    }

    @Autowired
    public ClientWebSocketSessionManager setMemberService(@Lazy MemberService memberService) {
        this.memberService = memberService;
        return this;
    }
}
