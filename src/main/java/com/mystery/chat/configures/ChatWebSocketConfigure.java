package com.mystery.chat.configures;

import com.mystery.chat.utils.UidGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Chat web socket configure
 *
 * @author shouchen
 * @date 2022/11/23
 */
@EnableWebSocket
@Configuration
public class ChatWebSocketConfigure implements WebSocketConfigurer {
    private final ChatWebSocketHandler webSocketHandler;

    public ChatWebSocketConfigure(ChatWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                        String name = servletRequest.getParameter("name");
                        attributes.put("name",
                                name == null || name.trim().isEmpty() ?
                                        "游客" + UidGenerator.fromNameSpace(String.valueOf(ThreadLocalRandom.current().nextInt()))
                                        : name);
                        return true;
                    }
                });
    }
}
