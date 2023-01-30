package com.example.addhayon.Models;


public class ProfileImageModel {
    private String profileImg;
    private String coverImg;


    public ProfileImageModel(String profileImg, String coverImg) {
        this.profileImg = profileImg;
        this.coverImg =coverImg;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String email) {
        this.coverImg = coverImg;
    }
}
