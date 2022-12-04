package com.mystery.chat.security.filters;

import com.alibaba.fastjson.JSON;
import com.mystery.chat.vos.ResultVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shouchen
 * @date 2022/11/27
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (checkAuthority((HttpServletRequest) request)) {
            chain.doFilter(request, response);
        } else {
            checkAuthorityFail((HttpServletResponse) response);
        }
    }

    private boolean checkAuthority(HttpServletRequest request) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || auth.isBlank() || auth.isEmpty()) {
//            checkAuthorityFail(response);
            auth = "user.read";
        }

//        SecurityContextHolder
//                .getContext()
//                .setAuthentication(UsernamePasswordAuthenticationToken
//                        .authenticated("smr",
//                                "",
//                                AuthorityUtils.createAuthorityList(auth)
//                        )
//                );
        return true;
    }

    private void checkAuthorityFail(HttpServletResponse response) throws IOException {
        response.getWriter().write(JSON.toJSONString(ResultVO.error(
                HttpStatus.UNAUTHORIZED.toString()
        ).setCode(
                HttpStatus.UNAUTHORIZED.value()
        )));
    }
}
