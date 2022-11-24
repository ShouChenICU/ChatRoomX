package com.mystery.chat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "chatroom.html";
    }
}
