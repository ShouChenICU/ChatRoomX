package com.mystery.chat.managers;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户WebSocket会话管理器
 *
 * @author shouchen
 * @date 2022/11/24
 */
@Component
public class ClientWebSocketSessionManager {
    private Map<String, WebSocketSession> sessionMap;

    public ClientWebSocketSessionManager() {
        sessionMap = new HashMap<>();
    }
}
