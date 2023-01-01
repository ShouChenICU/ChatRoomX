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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
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
    private final ThreadPoolExecutor executor;
    private UserService userService;
    private MemberService memberService;

    public ClientWebSocketSessionManager(AppConfig appConfig) {
        sessionMap = new RWMap<>();
        roomSessionMap = new RWMap<>();
        executor = new ThreadPoolExecutor(appConfig.broadcastThreadPollSize,
                appConfig.broadcastThreadPollSize,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(appConfig.broadcastThreadPollSize * 4),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public void putSession(String uid, WebSocketSession session) throws Exception {
        if (sessionMap.containsKey(uid)) {
            session.close();
        } else {
            sessionMap.put(uid, session);
            roomSessionMap.forEach((roomID, rwList) -> {
                if (memberService.userIsInRoom(uid, roomID)) {
                    rwList.add(session);
                }
            });
            LOGGER.info("{}:{} login", uid, userService.getByUID(uid)
                    .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                    .orElse("???"));
        }
    }

    public void removeSession(String uid) {
        WebSocketSession session = sessionMap.remove(uid);
        roomSessionMap.forEach((roomID, rwList) -> rwList.remove(session));
        LOGGER.info("{}:{} logout", uid, userService.getByUID(uid)
                .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                .orElse("???"));
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
        executor.execute(() -> roomSessionMap.computeIfAbsent(messageVO.getRoomID(),
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
                        session.sendMessage(new TextMessage(JSON.toJSONString(
                                ResultVO.of(messageVO)
                                        .setType("MSG")
                        )));
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
