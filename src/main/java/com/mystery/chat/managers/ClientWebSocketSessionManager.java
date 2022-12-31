package com.mystery.chat.managers;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.services.UserService;
import com.mystery.chat.utils.RWMap;
import com.mystery.chat.vos.MessageVO;
import com.mystery.chat.vos.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;

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
    private UserService userService;

    public ClientWebSocketSessionManager() {
        sessionMap = new RWMap<>();
    }

    public void putSession(String uid, WebSocketSession session) throws Exception {
        if (sessionMap.containsKey(uid)) {
            session.close();
        } else {
            sessionMap.put(uid, session);
            LOGGER.info("{}:{} login", uid, userService.getByUID(uid)
                    .flatMap(userEntity -> Optional.of(userEntity.getNickname()))
                    .orElse(""));
        }
    }

    public void removeSession(String uid) {
        sessionMap.remove(uid);
        LOGGER.info("{}:{} logout", uid, userService.getByUID(uid)
                .flatMap(userEntity -> Optional.of(userEntity.getNickname()))
                .orElse(""));
    }

    public boolean contents(String uid) {
        return sessionMap.containsKey(uid);
    }

    public void broadcastMsg(MessageVO messageVO) {
        // TODO: 2022/12/31
        sessionMap.forEach((k, v) -> {
            try {
                v.sendMessage(new TextMessage(
                        JSON.toJSONString(
                                ResultVO.of(messageVO)
                                        .setType("MSG")
                        ))
                );
            } catch (IOException e) {
                LOGGER.warn("", e);
            }
        });
    }

    @Autowired
    public ClientWebSocketSessionManager setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
