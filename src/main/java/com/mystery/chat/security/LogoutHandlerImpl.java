package com.mystery.chat.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Component
public class LogoutHandlerImpl implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
