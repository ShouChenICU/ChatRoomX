package com.mystery.chat.vos;

import com.mystery.chat.costant.Roles;
import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/11/29
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class UserVO {
    @NotNull(message = "UID cannot be empty", groups = ValidGroup.Update.class)
    private String uid;
    @NotBlank(groups = ValidGroup.Insert.class)
    @Email(message = "Email format error", groups = ValidGroup.Insert.class)
    private String email;
    @Size(max = 64, message = "Nickname length cannot exceed 64", groups = ValidGroup.Common.class)
    @NotBlank(message = "Nickname cannot be empty", groups = ValidGroup.Insert.class)
    private String nickname;
    @NotBlank(message = "Password cannot be empty", groups = ValidGroup.Insert.class)
    private String password;
    @Size(max = 16, groups = ValidGroup.Common.class, message = "Gender length cannot exceed 16")
    private String gender;
    private String role;
    @Size(max = 128, message = "Signature length cannot exceed 128", groups = ValidGroup.Common.class)
    private String signature;
    private String createDate;

    public UserVO() {
    }

    public UserVO(UserEntity userEntity) {
        Objects.requireNonNull(userEntity);
        this.setUid(userEntity.getUid())
                .setEmail(userEntity.getEmail())
                .setNickname(userEntity.getNickname())
                .setPassword(null)
                .setGender(userEntity.getGender())
                .setRole(Roles.parseRole(userEntity.getRole()))
                .setSignature(userEntity.getSignature())
                .setCreateDate(DateTimeFormatUtils.format(userEntity.getCreateInstant()));
    }

    public String getUid() {
        return uid;
    }

    public UserVO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserVO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserVO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserVO setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserVO setRole(String role) {
        this.role = role;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public UserVO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public UserVO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", signature='" + signature + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
