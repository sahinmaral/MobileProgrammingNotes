package com.example.todoapp.Models;

import java.util.Calendar;

public class Assignment {
    private String key;
    private String name;
    private String description;
    private String levelImportance;
    private String colorCode;
    private String startTime;
    private String endTime;
    private Boolean isFinished;
    public Assignment(String key, String name, String startTime, String description, String levelImportance, String colorCode, String endTime, Boolean isFinished){
        this.key = key;
        this.name = name;
        this.startTime = startTime;
        this.description = description;
        this.levelImportance = levelImportance;
        this.colorCode = colorCode;
        this.endTime = endTime;
        this.isFinished = isFinished;
    }

    public Assignment(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevelImportance() {
        return levelImportance;
    }

    public void setLevelImportance(String levelImportance) {
        this.levelImportance = levelImportance;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
