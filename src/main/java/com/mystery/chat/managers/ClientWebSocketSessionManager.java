package com.mystery.chat.managers;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户WebSocket会话管理器
 *
 * @author shouchen
 * @date 2022/11/24
 */
@Component
public class ClientWebSocketSessionManager {
    private final Map<String, WebSocketSession> sessionMap;

    public ClientWebSocketSessionManager() {
        sessionMap = new HashMap<>();
    }

    public void putSession(WebSocketSession session) {
        synchronized (sessionMap) {
            sessionMap.put(session.getId(), session);
        }
    }

    public void removeSession(WebSocketSession session) {
        synchronized (sessionMap) {
            sessionMap.remove(session.getId());
        }
    }

    public List<WebSocketSession> getSessions() {
        synchronized (sessionMap) {
            return new ArrayList<>(sessionMap.values());
        }
    }
}
