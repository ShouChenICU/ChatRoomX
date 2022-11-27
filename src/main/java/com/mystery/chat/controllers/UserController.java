package com.mystery.chat.controllers;

import com.mystery.chat.exceptions.BusinessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String hello() {
        return "user";
    }

    @DeleteMapping
    public String deleteUser() {
        throw new BusinessException("not fount");
//        return "del user";
    }
}
