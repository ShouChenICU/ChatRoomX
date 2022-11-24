package com.mystery.chat.vos;

/**
 * @author shouchen
 * @date 2022/11/23
 */
public class MessageVO {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public MessageVO setId(int id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageVO setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
