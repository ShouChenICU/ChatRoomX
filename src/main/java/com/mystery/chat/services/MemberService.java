package com.mystery.chat.services;

import com.mystery.chat.configures.AppConfig;
import com.mystery.chat.costant.Constants;
import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.MemberMapper;
import com.mystery.chat.utils.LRUCache;
import com.mystery.chat.vos.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/8
 */
@Service
public class MemberService {
    private final LRUCache<String, Optional<MemberEntity>> memberCache;
    private MemberMapper memberMapper;
    private UserService userService;

    public MemberService(AppConfig appConfig) {
        memberCache = new LRUCache<>(appConfig.userCacheSize);
    }

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
        memberCache.remove(memberEntity.getUid() + memberEntity.getRoomID());
    }

    /**
     * 查询用户是否在房间内
     *
     * @param uid    用户id
     * @param roomID 房间id
     * @return 用户在房间返回true, 否则返回false
     */
    public boolean userIsInRoom(String uid, String roomID) {
        if (Constants.GLOBAL_ROOM_ID.equalsIgnoreCase(roomID)) {
            return true;
        }
        return memberCache.getElsePut(uid + roomID, () -> memberMapper.get(uid, roomID)).isPresent();
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
