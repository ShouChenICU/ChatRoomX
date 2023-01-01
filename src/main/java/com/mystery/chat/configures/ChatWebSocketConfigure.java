package com.mystery.chat.configures;

import com.mystery.chat.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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
                        super.beforeHandshake(request, response, wsHandler, attributes);
                        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                        AtomicBoolean result = new AtomicBoolean(false);
                        Arrays.stream(servletRequest.getCookies())
                                .filter(cookie -> HttpHeaders.AUTHORIZATION.equalsIgnoreCase(cookie.getName()))
                                .findFirst()
                                .ifPresent(cookie -> {
                                    Claims claims = TokenUtils.parseToken(cookie.getValue());
                                    attributes.put("uid", claims.getAudience());
                                    attributes.put("expire", claims.getExpiration().getTime());
                                    attributes.put("heart-beat", System.currentTimeMillis());
                                    result.set(true);
                                });
                        return result.get();
                    }
                });
    }
}
