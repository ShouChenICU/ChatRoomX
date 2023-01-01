package com.mystery.chat.security.filters;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.utils.TokenUtils;
import com.mystery.chat.vos.ResultVO;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 权限认证过滤器
 *
 * @author shouchen
 * @date 2022/11/27
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            checkAuthority((HttpServletRequest) request);
        } catch (Exception ignore) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (HttpMethod.GET.matches(httpServletRequest.getMethod())) {
                chain.doFilter(request, response);
                return;
            }
            response.getWriter().write(JSON.toJSONString(
                    ResultVO.error(
                            HttpStatus.UNAUTHORIZED.toString()
                    ).setCode(
                            HttpStatus.UNAUTHORIZED.value()
                    )
            ));
            return;
        }
        chain.doFilter(request, response);
    }

    private void checkAuthority(HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), HttpHeaders.AUTHORIZATION))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new AccountExpiredException(""));
        Claims claims = TokenUtils.parseToken(token);
        SecurityContextHolder
                .getContext()
                .setAuthentication(UsernamePasswordAuthenticationToken
                        .authenticated(claims.getAudience(),
                                "",
                                AuthorityUtils.createAuthorityList(claims.getSubject().split(","))
                        )
                );
    }
}
