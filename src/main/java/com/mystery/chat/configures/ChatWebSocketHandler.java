package com.mystery.chat.configures;

import com.mystery.chat.managers.ClientWebSocketSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    private final ClientWebSocketSessionManager sessionManager;

    public ChatWebSocketHandler(ClientWebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.putSession((String) session.getAttributes().get("uid"), session);

        session.sendMessage(new PingMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (!status.equals(CloseStatus.NOT_ACCEPTABLE)) {
            sessionManager.removeSession((String) session.getAttributes().get("uid"));
        }
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) {
        session.getAttributes().put("heart-beat", System.currentTimeMillis());
    }
}
