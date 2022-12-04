package com.mystery.chat.security;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.utils.JWTUtils;
import com.mystery.chat.vos.ResultVO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 认证成功处理
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static final int COOKIE_EXP_AGE = (int) TimeUnit.HOURS.toSeconds(1);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION,
                JWTUtils.genToken(authentication.getName(),
                        authentication.getAuthorities())
        );
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_EXP_AGE);
        response.addCookie(cookie);
        response.getWriter().write(JSON.toJSONString(ResultVO.success()));
    }
}
