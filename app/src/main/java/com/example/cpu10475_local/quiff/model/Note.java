package com.example.cpu10475_local.quiff.model;

import android.os.PersistableBundle;

import java.io.Serializable;

public class Note  implements Serializable {
    private String titles;
    private String date;
    private String type;
    private String optionalInfo;
    private int level = -1;

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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + titles.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + optionalInfo.hashCode();
        result = 31 * result + level;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
       if (obj == this)
           return true;
       if(!(obj instanceof Note))
           return false;
       Note note = (Note) obj;
       return note.getTitles().equals(titles);
    }
}
