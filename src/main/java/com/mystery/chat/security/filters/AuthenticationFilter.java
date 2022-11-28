package com.mystery.chat.security.filters;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author shouchen
 * @date 2022/11/27
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken.authenticated("smr",
                        "",
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN"))
        );

//        response.getWriter().write(JSON.toJSONString(ResultVO.error(HttpStatus.UNAUTHORIZED.toString()).setCode(HttpStatus.UNAUTHORIZED.value())));

        chain.doFilter(request, response);
    }
}
