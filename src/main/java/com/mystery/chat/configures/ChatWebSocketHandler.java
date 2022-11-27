package com.mystery.chat.configures;

import com.mystery.chat.managers.ClientWebSocketSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
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
        sessionManager.putSession(session);
        String msg = "Welcome " + session.getAttributes().get("name");
        LOGGER.info(msg);
        broadcastMsg(msg);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String msg = session.getAttributes().get("name") + " closed";
        LOGGER.info(msg);
        sessionManager.removeSession(session);
        broadcastMsg(msg);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        broadcastMsg(session.getAttributes().get("name") + ":\n" + message.getPayload());
    }

    private void broadcastMsg(String msg) throws Exception {
        TextMessage echo = new TextMessage(msg);
//        sessionManager.getSessionMap().forEach((key, value) -> {
//            try {
//                if(value.isOpen()) {
//                    value.sendMessage(echo);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        for (WebSocketSession s : sessionManager.getSessions()) {
            if (s.isOpen()) {
                s.sendMessage(echo);
            }
        }
    }
}
