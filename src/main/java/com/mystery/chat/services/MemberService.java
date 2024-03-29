package com.mystery.chat.services;

import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.MemberMapper;
import com.mystery.chat.vos.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/8
 */
@Service
@CacheConfig(cacheNames = "MEMBER_CACHE")
public class MemberService {
    private MemberMapper memberMapper;
    private UserService userService;
    private RoomService roomService;

    /**
     * 查询房间成员
     *
     * @param uid    用户UID
     * @param roomID 房间ID
     * @return 成员
     */
    @Cacheable(key = "#uid + #roomID")
    public Optional<MemberEntity> get(String uid, String roomID) {
        return memberMapper.get(uid, roomID);
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
                                .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                                .orElse("")))
                .collect(Collectors.toList());
    }

    /**
     * 添加成员
     *
     * @param memberEntity 成员
     */
    public void addMember(MemberEntity memberEntity) {
        userService.getByUID(memberEntity.getUid()).orElseThrow(() -> new BusinessException("User not found"));
        roomService.getByID(memberEntity.getRoomID()).orElseThrow(() -> new BusinessException("Room not found"));
        memberEntity.setJoinInstant(System.currentTimeMillis());
        if (memberMapper.insert(memberEntity) < 1) {
            throw new BusinessException("Add member failure");
        }
    }

    /**
     * 查询用户是否在房间内
     *
     * @param uid    用户id
     * @param roomID 房间id
     * @return 用户在房间返回true, 否则返回false
     */
    public boolean userIsInRoom(String uid, String roomID) {
        return get(uid, roomID).isPresent();
    }

    /**
     * 房间成员是否有任意身份
     *
     * @param uid    用户UID
     * @param roomID 房间ID
     * @param roles  权限列表
     * @return 成员有身份则返回true, 否则返回false
     */
    public boolean userHasAnyRole(String uid, String roomID, String... roles) {
        AtomicBoolean result = new AtomicBoolean(false);
        get(uid, roomID).ifPresent(memberEntity -> {
            for (String role : roles) {
                if (Objects.equals(role, memberEntity.getRole())) {
                    result.set(true);
                    return;
                }
            }
        });
        return result.get();
    }

    @Autowired
    public MemberService setMemberMapper(@Lazy MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
        return this;
    }

    @Autowired
    public MemberService setUserService(@Lazy UserService userService) {
        this.userService = userService;
        return this;
    }

    @Autowired
    public MemberService setRoomService(@Lazy RoomService roomService) {
        this.roomService = roomService;
        return this;
    }
}
