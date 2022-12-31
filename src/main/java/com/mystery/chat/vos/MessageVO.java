package com.mystery.chat.vos;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 消息VO
 *
 * @author shouchen
 * @date 2022/11/23
 */
public class MessageVO {
    private long id;
    @NotBlank(message = "Room id cannot be empty", groups = ValidGroup.Insert.class)
    private String roomID;
    private String uid;
    private String sender;
    private String role;
    private String type;
    @NotNull(message = "Content cannot be null", groups = ValidGroup.Insert.class)
    private String content;
    private String dateTime;
    private long instant;

    public MessageVO() {
    }

    public MessageVO(MessageEntity entity) {
        this.setId(entity.getId())
                .setRoomID(entity.getRoomID())
                .setUid(entity.getUid())
                .setType(entity.getType())
                .setContent(entity.getContent())
                .setDateTime(DateTimeFormatUtils.formatMsg(entity.getInstant()))
                .setInstant(entity.getInstant());
    }

    public long getId() {
        return id;
    }

    public MessageVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getRoomID() {
        return roomID;
    }

    public MessageVO setRoomID(String roomID) {
        this.roomID = roomID;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public MessageVO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public MessageVO setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getRole() {
        return role;
    }

    public MessageVO setRole(String role) {
        this.role = role;
        return this;
    }

    public String getType() {
        return type;
    }

    public MessageVO setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageVO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public MessageVO setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public long getInstant() {
        return instant;
    }

    public MessageVO setInstant(long instant) {
        this.instant = instant;
        return this;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", roomID='" + roomID + '\'' +
                ", uid='" + uid + '\'' +
                ", sender='" + sender + '\'' +
                ", role='" + role + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", instant=" + instant +
                '}';
    }
}
