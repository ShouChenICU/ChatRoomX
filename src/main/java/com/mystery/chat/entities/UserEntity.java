package com.mystery.chat.entities;

import com.mystery.chat.costant.Genders;
import com.mystery.chat.vos.UserVO;

import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/11/24
 */
public class UserEntity {
    private String uid;
    private String email;
    private String nickname;
    private String password;
    private String gender;
    private long createInstant;

    public UserEntity() {
    }

    public UserEntity(UserVO userVO) {
        Objects.requireNonNull(userVO);
        this.setUid(userVO.getUid())
                .setEmail(userVO.getEmail())
                .setNickname(userVO.getNickname())
                .setPassword(userVO.getPassword())
                .setGender(Genders.checkGender(userVO.getGender()));
    }

    public String getUid() {
        return uid;
    }

    public UserEntity setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public long getCreateInstant() {
        return createInstant;
    }

    public UserEntity setCreateInstant(long createInstant) {
        this.createInstant = createInstant;
        return this;
    }
}
