package com.mystery.chat.services;

import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.MemberMapper;
import com.mystery.chat.vos.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/8
 */
@Service
public class MemberService {
    private MemberMapper memberMapper;
    private UserService userService;

    /**
     * 根据房间ID查询成员列表
     *
     * @param roomID 房间ID
     * @return 成员列表
     */
    public List<MemberEntity> listByRoomID(String roomID) {
        return memberMapper.listByRoomID(roomID);
    }

    /**
     * 根据房间ID查询成员VO列表
     *
     * @param roomID 房间ID
     * @return 成员VO列表
     */
    public List<MemberVO> listVOsByRoomID(String roomID) {
        return listByRoomID(roomID)
                .stream()
                .sorted((a, b) -> MemberRoles.calcWeight(b.getRole())
                        - MemberRoles.calcWeight(a.getRole())
                        + Long.compare(a.getJoinInstant(), b.getJoinInstant()))
                .map(entity -> new MemberVO(entity)
                        .setNickname(userService
                                .getByUID(entity.getUid())
                                .stream()
                                .map(UserEntity::getNickname)
                                .findFirst()
                                .orElse("")))
                .collect(Collectors.toList());
    }

    /**
     * 添加成员
     *
     * @param memberEntity 成员
     */
    public void addMember(MemberEntity memberEntity) {
        if (memberMapper.addMember(memberEntity) < 1) {
            throw new BusinessException("Add member failure");
        }
    }

    @Autowired
    public MemberService setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
        return this;
    }

    @Autowired
    public MemberService setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
