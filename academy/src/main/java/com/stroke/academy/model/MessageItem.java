package com.stroke.academy.model;

import java.io.Serializable;

/**
 * Created by emilyu on 6/11/15.
 */
public class MessageItem implements Serializable{

    private String type;

    private String title;

    private String content;

    public MessageItem(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
