package com.mystery.chat.vos;

import com.mystery.chat.costant.Genders;
import com.mystery.chat.costant.Roles;
import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/11/29
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class UserVO {
    @NotNull(message = "UID不能为null", groups = ValidGroup.Update.class)
    private String uid;
    @NotBlank(groups = ValidGroup.Insert.class)
    @Email(message = "邮箱格式错误", groups = ValidGroup.Insert.class)
    private String email;
    @Max(value = 64, message = "昵称长度不能超过64", groups = ValidGroup.Common.class)
    @NotBlank(message = "昵称不能为空", groups = ValidGroup.Insert.class)
    private String nickname;
    @NotNull(message = "密码不能为空", groups = ValidGroup.Insert.class)
    private String password;
    private String gender;
    private String role;
    @Max(value = 1024, message = "签名长度不能超过1024")
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
                .setGender(Genders.parseGender(userEntity.getGender()))
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
