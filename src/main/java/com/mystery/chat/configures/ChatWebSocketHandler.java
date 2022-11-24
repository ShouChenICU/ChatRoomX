package com.mystery.chat.configures;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.vos.MessageVO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author shouchen
 * @date 2022/11/23
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.err.println(session);
        System.err.println(session.getTextMessageSizeLimit());
        System.err.println(session.getBinaryMessageSizeLimit());
        System.err.println(session.getId());
        session.sendMessage(new TextMessage(JSON.toJSONString(new MessageVO()
                .setId(123)
                .setContent("hello"))));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + " closed");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.err.println(session.getAttributes());

        System.err.println(message.getPayload());
    }
}
