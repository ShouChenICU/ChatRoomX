package com.mystery.chat.entities;

import com.mystery.chat.vos.RoomVO;

import java.util.Objects;

/**
 * @author shouchen
 * @date 2022/11/26
 */
public class RoomEntity {
    private String id;
    private String title;
    private String introduction;
    private boolean isPublic;
    private long createInstant;

    public RoomEntity() {
    }

    public RoomEntity(RoomVO roomVO) {
        Objects.requireNonNull(roomVO);
        this.setTitle(roomVO.getTitle())
                .setIntroduction(Objects.requireNonNullElse(roomVO.getIntroduction(), ""))
                .setPublic(roomVO.getIsPublic());
    }

    public String getId() {
        return id;
    }

    public RoomEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public RoomEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIntroduction() {
        return introduction;
    }

    public RoomEntity setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public RoomEntity setPublic(boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public long getCreateInstant() {
        return createInstant;
    }

    public RoomEntity setCreateInstant(long createInstant) {
        this.createInstant = createInstant;
        return this;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isPublic=" + isPublic +
                ", createInstant=" + createInstant +
                '}';
    }
}
