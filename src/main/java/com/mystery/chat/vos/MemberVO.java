package com.mystery.chat.vos;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author shouchen
 * @date 2022/12/8
 */
public class MemberVO {
    @NotBlank(groups = ValidGroup.Insert.class, message = "UID cannot be empty")
    private String uid;
    @NotBlank(groups = ValidGroup.Insert.class, message = "Room ID cannot be empty")
    private String roomID;
    private String nickname;
    private String role;
    private String label;
    private String joinDate;

    public MemberVO() {
    }

    public MemberVO(MemberEntity memberEntity) {
        this.setUid(memberEntity.getUid())
                .setRoomID(memberEntity.getRoomID())
                .setRole(memberEntity.getRole())
                .setLabel(memberEntity.getLabel())
                .setJoinDate(DateTimeFormatUtils.format(memberEntity.getJoinInstant()));
    }

    public String getUid() {
        return uid;
    }

    public MemberVO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getRoomID() {
        return roomID;
    }

    public MemberVO setRoomID(String roomID) {
        this.roomID = roomID;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public MemberVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getRole() {
        return role;
    }

    public MemberVO setRole(String role) {
        this.role = role;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public MemberVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public MemberVO setJoinDate(String joinDate) {
        this.joinDate = joinDate;
        return this;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "uid='" + uid + '\'' +
                ", roomID='" + roomID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", label='" + label + '\'' +
                ", joinDate='" + joinDate + '\'' +
                '}';
    }
}
