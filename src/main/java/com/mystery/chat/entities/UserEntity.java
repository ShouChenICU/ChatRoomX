package com.mystery.chat.entities;

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
    private String role;
    private String signature;
    private String status;
    private long createInstant;

    public UserEntity() {
    }

    public UserEntity(UserVO userVO) {
        Objects.requireNonNull(userVO);
        this.setUid(userVO.getUid())
                .setEmail(userVO.getEmail())
                .setNickname(userVO.getNickname())
                .setPassword(userVO.getPassword())
                .setGender(Objects.requireNonNullElse(userVO.getGender(), "Unknown").trim())
                .setSignature(Objects.requireNonNullElse(userVO.getSignature(), ""));
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

    public String getRole() {
        return role;
    }

    public UserEntity setRole(String role) {
        this.role = role;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public UserEntity setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public long getCreateInstant() {
        return createInstant;
    }

    public UserEntity setCreateInstant(long createInstant) {
        this.createInstant = createInstant;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", signature='" + signature + '\'' +
                ", status='" + status + '\'' +
                ", createInstant=" + createInstant +
                '}';
    }
}
