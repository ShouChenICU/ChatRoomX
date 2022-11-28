package com.mystery.chat.controllers;

import com.mystery.chat.vos.ResultVO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@Controller
public class IndexController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @GetMapping("/")
    public String indexPage(HttpServletResponse response) {
        return "chatroom.html";
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String loginPage() {
        return "login.html";
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public void handle404ByHtml(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<?> handle404ByJson(HttpServletResponse response) {
        return ResultVO.error(HttpStatus.valueOf(response.getStatus()).toString());
    }
}
