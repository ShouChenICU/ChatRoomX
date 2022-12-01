package com.mystery.chat.controllers;

import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.services.UserService;
import com.mystery.chat.vos.ResultVO;
import com.mystery.chat.vos.UserVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

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

    /**
     * 根据uid查询用户实体
     *
     * @param uid uid
     * @return 用户VO
     */
    @GetMapping("/{uid}")
    @PreAuthorize("hasAuthority('sys.user.get') or hasRole(@roles.ADMIN)")
    public ResultVO<UserVO> getUserByUID(@PathVariable String uid) {
        return ResultVO.of(
                new UserVO(Optional
                        .ofNullable(userService.getByUID(uid))
                        .orElseThrow(() -> new BusinessException("User not found"))
                )
        );
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys.user.register') or hasRole(@roles.ADMIN)")
    public ResultVO<?> registerUser(@Valid @RequestBody UserVO userVO) {
        userService.registerUser(new UserEntity(userVO));
        return ResultVO.of("注册成功");
    }

    @DeleteMapping("/{uid}")
    public String deleteUserByUID(@PathVariable String uid) {
//        throw new BusinessException("not fount");
        return "del user";
    }
}
