package com.mystery.chat.controllers;

import com.mystery.chat.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uid}")
    @PreAuthorize("hasAuthority('user.read') or hasRole(@roles.ADMIN)")
    public String getUserByUID(@PathVariable String uid) {
        return "user";
    }

    @DeleteMapping("/{uid}")
    public String deleteUserByUID(@PathVariable String uid) {
//        throw new BusinessException("not fount");
        return "del user";
    }
}
