package com.mystery.chat.controllers;

import com.mystery.chat.vos.ResultVO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@Controller
public class IndexController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @PermitAll
    @GetMapping("/")
    public String indexPage(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "login.html";
        }
        return "chatroom.html";
    }

    @PermitAll
    @ResponseBody
    @PostMapping("/ping")
    public ResultVO<?> ping() {
        return ResultVO.success();
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public void handleErrorByHtml(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<?> handleErrorByJson(HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        response.setStatus(HttpStatus.OK.value());
        return ResultVO.error(
                httpStatus.toString()
        ).setCode(
                httpStatus.value()
        );
    }
}
