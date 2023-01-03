package com.mystery.chat.controllers;

import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.services.MemberService;
import com.mystery.chat.vos.MemberVO;
import com.mystery.chat.vos.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shouchen
 * @date 2022/12/31
 */
@RestController
@RequestMapping("/api/member")
public class MemberController {
    private MemberService memberService;

    /**
     * 添加房间成员
     *
     * @param authentication authentication
     * @param memberVO       成员
     * @return 结果
     */
    @PostMapping("/add")
    @PreAuthorize("@userService.getByUID(#memberVO.uid).present" +
            "&&@roomService.getByID(#memberVO.roomID).present" +
            "&&((hasAnyRole(@roles.USER)" +
            "&& @memberService.userHasAnyRole(#authentication.principal, #memberVO.roomID," +
            "@memberRoles.ADMIN, @memberRoles.OWNER))" +
            "|| hasAnyRole(@roles.ADMIN))")
    public ResultVO<?> addMember(
            Authentication authentication,
            @RequestBody @Validated(ValidGroup.Insert.class) MemberVO memberVO) {
        memberService.addMember(
                new MemberEntity(memberVO)
                        .setLabel("")
                        .setRole(MemberRoles.MEMBER));
        return ResultVO.success();
    }

    @Autowired
    public MemberController setMemberService(MemberService memberService) {
        this.memberService = memberService;
        return this;
    }
}
