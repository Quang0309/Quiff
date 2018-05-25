package com.example.cpu10475_local.quiff.model;

public class Note {
    private String titles;
    private String date;
    private String type;
    private String optionalInfo;
    private int level;

    public Note(String titles, String date, String type, String optionalInfo, int level ) {
        this.titles = titles;
        this.date = date;
        this.type = type;
        this.optionalInfo = optionalInfo;
        this.level = level;
    }
    public Note()
    {}
    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptionalInfo() {
        return optionalInfo;
    }

    public void setOptionalInfo(String optionalInfo) {
        this.optionalInfo = optionalInfo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
