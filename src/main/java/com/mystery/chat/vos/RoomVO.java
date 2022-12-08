package com.mystery.chat.vos;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.utils.DateTimeFormatUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/12/7
 */
public class RoomVO {
    @NotBlank(groups = ValidGroup.Update.class, message = "Room id cannot be empty")
    private String id;
    @NotBlank(groups = ValidGroup.Common.class, message = "Room title cannot be empty")
    @Max(value = 64, groups = ValidGroup.Common.class, message = "Room title length cannot exceed 64")
    private String title;
    private String introduction;
    private boolean isPublic;
    private String createDate;

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

    public boolean isPublic() {
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

    @Override
    public String toString() {
        return "RoomVO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isPublic=" + isPublic +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
