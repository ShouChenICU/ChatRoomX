package com.mystery.chat.vos;

import com.mystery.chat.costant.Genders;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.utils.DateTimeFormatUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/11/29
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class UserVO {
    @NotNull(message = "UID不能为null")
    private String uid;
    @Email(message = "邮箱格式错误")
    private String email;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    private String password;
    private String gender;
    private String createDate;

    public UserVO() {
    }

    public UserVO(UserEntity userEntity) {
        Objects.requireNonNull(userEntity);
        this.setUid(userEntity.getUid())
                .setEmail(userEntity.getEmail())
                .setNickname(userEntity.getNickname())
                .setPassword(userEntity.getPassword())
                .setGender(Genders.parseGender(userEntity.getGender()))
                .setCreateDate(DateTimeFormatUtil.format(userEntity.getCreateInstant()));
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
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
