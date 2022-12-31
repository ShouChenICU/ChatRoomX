package com.mystery.chat.entities;

import com.mystery.chat.vos.MemberVO;

/**
 * @author shouchen
 * @date 2022/11/27
 */
public class MemberEntity {
    private String uid;
    private String roomID;
    private String role;
    private String label;
    private long joinInstant;

    public MemberEntity() {
    }

    public MemberEntity(MemberVO memberVO) {
        this.setUid(memberVO.getUid())
                .setRoomID(memberVO.getRoomID());
    }

    public String getUid() {
        return uid;
    }

    public MemberEntity setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getRoomID() {
        return roomID;
    }

    public MemberEntity setRoomID(String roomID) {
        this.roomID = roomID;
        return this;
    }

    public String getRole() {
        return role;
    }

    public MemberEntity setRole(String role) {
        this.role = role;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public MemberEntity setLabel(String label) {
        this.label = label;
        return this;
    }

    public long getJoinInstant() {
        return joinInstant;
    }

    public MemberEntity setJoinInstant(long joinInstant) {
        this.joinInstant = joinInstant;
        return this;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "uid='" + uid + '\'' +
                ", roomID='" + roomID + '\'' +
                ", role='" + role + '\'' +
                ", label='" + label + '\'' +
                ", joinInstant=" + joinInstant +
                '}';
    }
}
