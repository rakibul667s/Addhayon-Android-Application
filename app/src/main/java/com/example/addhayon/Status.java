package com.example.addhayon;

import java.util.Date;

public class Status {
    private String imageUrl;
    private long timeStamp;
    private String key;
    private Date date;

    public Status() {
    }

    public Status(String imageUrl, long timeStamp, String key,Date date) {
        this.imageUrl = imageUrl;
        this.timeStamp = timeStamp;
        this.key = key;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
