package com.mystery.chat.security.filters;

import com.mystery.chat.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
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
 * @author shouchen
 * @date 2022/11/27
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            checkAuthority((HttpServletRequest) request);
        } catch (Exception ignore) {
        }
        chain.doFilter(request, response);
    }

    private void checkAuthority(HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), HttpHeaders.AUTHORIZATION))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new AccountExpiredException(""));
        Claims claims = JWTUtils.parseToken(token);
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
