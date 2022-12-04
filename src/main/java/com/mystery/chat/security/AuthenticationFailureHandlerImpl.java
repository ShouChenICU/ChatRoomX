package com.mystery.chat.security;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.vos.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().println(JSON.toJSONString(ResultVO.error("Incorrect username or password")));
    }
}
