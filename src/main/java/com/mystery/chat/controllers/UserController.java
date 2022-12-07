package com.mystery.chat.controllers;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.services.UserService;
import com.mystery.chat.vos.ResultVO;
import com.mystery.chat.vos.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author shouchen
 * @date 2022/11/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    /**
     * 根据uid查询用户实体
     *
     * @param uid uid
     * @return 用户VO
     */
    @PostMapping(value = "/get")
    @PreAuthorize("hasAnyRole(@roles.USER)")
    public ResultVO<UserVO> getUserByUID(@RequestParam String uid) {
        return ResultVO.of(
                new UserVO(Optional
                        .ofNullable(userService.getByUID(uid))
                        .orElseThrow(() -> new BusinessException("User not found"))
                )
        );
    }

    /**
     * 添加用户
     *
     * @param userVO 用户VO
     * @return 结果
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole(@roles.ADMIN)")
    public ResultVO<?> addUser(@Validated(ValidGroup.Insert.class) @RequestBody UserVO userVO) {
        userService.addUser(new UserEntity(userVO));
        return ResultVO.of("注册成功");
    }

    /**
     * 更新用户
     *
     * @param userVO         用户信息
     * @param authentication 权限信息
     * @return 结果
     */
    @PostMapping("/update")
    @PreAuthorize("#userVO.uid.equals(#authentication.principal) or hasRole(@roles.ADMIN)")
    public ResultVO<?> updateUser(@Validated(ValidGroup.Update.class) @RequestBody UserVO userVO, Authentication authentication) {
        userService.updateUser(new UserEntity(userVO));
        return ResultVO.success();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole(@roles.ADMIN)")
    public String deleteUserByUID(@RequestParam String uid) {
        // TODO: 2022/12/3  
//        throw new BusinessException("not fount");
        return "del user";
    }

    @Autowired
    public UserController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
