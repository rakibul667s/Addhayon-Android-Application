package com.example.addhayon;

import java.util.ArrayList;

public class UserStatus {
    private String name, profileImage, key;
    private long lastUpdated;
    private ArrayList<Status> statuses;

    public UserStatus() {
    }

    public UserStatus(String name, String profileImage, long lastUpdated, ArrayList<Status> statuses,String key) {
        this.name = name;
        this.profileImage = profileImage;
        this.lastUpdated = lastUpdated;
        this.statuses = statuses;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }
}
