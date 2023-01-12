package com.mystery.chat.entities;

import com.mystery.chat.vos.MessageVO;

/**
 * 消息
 *
 * @author shouchen
 * @date 2022/11/24
 */
public class MessageEntity {
    private long instant;
    private String roomID;
    private String uid;
    private String type;
    private String content;

    public MessageEntity() {
    }

    public MessageEntity(MessageVO messageVO) {
        this.setRoomID(messageVO.getRoomID())
                .setContent(messageVO.getContent());
    }

    public String getRoomID() {
        return roomID;
    }

    public MessageEntity setRoomID(String roomID) {
        this.roomID = roomID;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public MessageEntity setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getType() {
        return type;
    }

    public MessageEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public long getInstant() {
        return instant;
    }

    public MessageEntity setInstant(long instant) {
        this.instant = instant;
        return this;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                ", roomID='" + roomID + '\'' +
                ", uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", instant=" + instant +
                '}';
    }
}
