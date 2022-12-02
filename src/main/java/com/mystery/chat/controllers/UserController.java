package com.mystery.chat.controllers;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.services.UserService;
import com.mystery.chat.vos.ResultVO;
import com.mystery.chat.vos.UserVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 注册用户
     *
     * @param userVO 用户VO
     * @return 结果
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys.user.register') or hasRole(@roles.ADMIN)")
    public ResultVO<?> registerUser(@Validated(ValidGroup.Insert.class) @RequestBody UserVO userVO) {
        userService.registerUser(new UserEntity(userVO));
        return ResultVO.of("注册成功");
    }

    public ResultVO<?> editUser(@Validated(ValidGroup.Update.class) @RequestBody UserVO userVO) {
        // TODO: 2022/12/3  
        return ResultVO.success();
    }

    @DeleteMapping("/{uid}")
    public String deleteUserByUID(@PathVariable String uid) {
        // TODO: 2022/12/3  
//        throw new BusinessException("not fount");
        return "del user";
    }
}
