package com.mystery.chat.security;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.vos.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证成功处理
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // TODO: 2022/11/27
        response.getWriter().write(JSON.toJSONString(ResultVO.success()));
    }
}
