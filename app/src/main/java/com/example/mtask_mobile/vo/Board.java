package com.example.mtask_mobile.vo;

public class Board {
    private String uuid;
    private String boardName;
    private String desc;
    private String descPlainText;
    private String color;
    private boolean isFavorites;
    private String constructorName;
    private int taskCnt;
    private String createdDateTime;
    private String updatedDateTime;

    public Board() {
    }

    public Board(String uuid, String boardName, String desc, String descPlainText, String color, boolean isFavorites, String constructorName, int taskCnt, String createdDateTime, String updatedDateTime) {
        this.uuid = uuid;
        this.boardName = boardName;
        this.desc = desc;
        this.descPlainText = descPlainText;
        this.color = color;
        this.isFavorites = isFavorites;
        this.constructorName = constructorName;
        this.taskCnt = taskCnt;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescPlainText() {
        return descPlainText;
    }

    public void setDescPlainText(String descPlainText) {
        this.descPlainText = descPlainText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    public int getTaskCnt() {
        return taskCnt;
    }

    public void setTaskCnt(int taskCnt) {
        this.taskCnt = taskCnt;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
