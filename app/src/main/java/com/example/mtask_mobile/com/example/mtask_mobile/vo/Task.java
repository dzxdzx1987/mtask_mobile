package com.example.mtask_mobile.com.example.mtask_mobile.vo;

public class Task {
    private String name;
    private String ownerImageUrl;
    private String content;

    public Task(String name, String ownerImageUrl, String content) {
        this.name = name;
        this.ownerImageUrl = ownerImageUrl;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerImageUrl() {
        return ownerImageUrl;
    }

    public void setOwnerImageUrl(String ownerImageUrl) {
        this.ownerImageUrl = ownerImageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
