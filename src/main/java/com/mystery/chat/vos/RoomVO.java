package com.mystery.chat.vos;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/12/7
 */
public class RoomVO {
    @NotBlank(groups = ValidGroup.Update.class, message = "Room id cannot be empty")
    private String id;
    @NotBlank(groups = ValidGroup.Common.class, message = "Room title cannot be empty")
    @Size(max = 64, groups = ValidGroup.Common.class, message = "Room title length cannot exceed 64")
    private String title;
    @Size(max = 1024, groups = ValidGroup.Common.class, message = "Introduction length cannot exceed 1024")
    private String introduction;
    private boolean isPublic;
    private String createDate;
    private List<MemberVO> members;
    private List<MessageVO> messages;

    public RoomVO() {
    }

    public RoomVO(RoomEntity roomEntity) {
        Objects.requireNonNull(roomEntity);
        this.setId(roomEntity.getId())
                .setTitle(roomEntity.getTitle())
                .setIntroduction(roomEntity.getIntroduction())
                .setPublic(roomEntity.isPublic())
                .setCreateDate(DateTimeFormatUtils.format(roomEntity.getCreateInstant()));
    }

    public String getId() {
        return id;
    }

    public RoomVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public RoomVO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIntroduction() {
        return introduction;
    }

    public RoomVO setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public RoomVO setPublic(boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public RoomVO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public List<MemberVO> getMembers() {
        return members;
    }

    public RoomVO setMembers(List<MemberVO> members) {
        this.members = members;
        return this;
    }

    public List<MessageVO> getMessages() {
        return messages;
    }

    public RoomVO setMessages(List<MessageVO> messages) {
        this.messages = messages;
        return this;
    }

    @Override
    public String toString() {
        return "RoomVO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isPublic=" + isPublic +
                ", createDate='" + createDate + '\'' +
                ", members=" + members +
                ", messages=" + messages +
                '}';
    }
}
