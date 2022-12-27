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

import javax.annotation.security.PermitAll;

/**
 * 用户接口
 *
 * @author shouchen
 * @date 2022/11/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    /**
     * 查询当前登陆用户的信息
     *
     * @return 当前登陆用户信息
     */
    @PostMapping("/me")
    @PermitAll
    public ResultVO<UserVO> me() {
        return ResultVO.of(
                new UserVO(userService
                        .me()
                        .orElseThrow(() -> new BusinessException("User not found"))
                )
        );
    }

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
                new UserVO(userService.getByUID(uid)
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
    public ResultVO<String> addUser(@Validated(ValidGroup.Insert.class) @RequestBody UserVO userVO) {
        return ResultVO.of(userService.addUser(new UserEntity(userVO)));
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
    public ResultVO<?> deleteUserByUID(@RequestParam String uid) {
        // TODO: 2022/12/3  
//        throw new BusinessException("not fount");
        return ResultVO.error("");
    }

    @Autowired
    public UserController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
