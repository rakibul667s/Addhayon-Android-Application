package com.example.addhayon.Models;

import android.graphics.Bitmap;

public class ProfileModel {
    private String name;
    private String email;
    private String bio;
    private String address;
    private String dateofBirth;
    private String sclClg;
    private String phn;
    private String profileImg;
    private String coverImg;
    private int bookmarksCount;
    private String language;
    private String id;
    private String event;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String bg;
    private String dev;
    private String upcheck;
    private String uplink;
    private String vname;
    private String mb;






    public ProfileModel(String name, String email, String bio,String address,String dateofBirth,String sclClg,String phn,int bookmarksCount,String profileImg,String coverImg, String language, String id,String event,String img1,String img2,String img3,String img4,String img5, String bg, String dev,String upcheck,String uplink,String vname,String mb) {
        this.name = name;
        this.email = email;
        this.bio =bio;
        this.address= address;
        this.dateofBirth=dateofBirth;
        this.sclClg=sclClg;
        this.phn=phn;
        this.bookmarksCount = bookmarksCount;
        this.profileImg = profileImg;
        this.coverImg = coverImg;
        this.language = language;
        this.id = id;
        this.event = event;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.bg = bg;
        this.dev = dev;
        this.upcheck = upcheck;
        this.uplink = uplink;
        this.vname = vname;
        this.mb = mb;


    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getUpcheck() {
        return upcheck;
    }

    public void setUpcheck(String upcheck) {
        this.upcheck = upcheck;
    }

    public String getUplink() {
        return uplink;
    }

    public void setUplink(String uplink) {
        this.uplink = uplink;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getBookmarksCount() {
        return bookmarksCount;
    }

    public void setBookmarksCount(int bookmarksCount) {
        this.bookmarksCount = bookmarksCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }
    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getSclClg() {
        return sclClg;
    }
    public void setSclClg(String sclClg) {
        this.sclClg = sclClg;
    }

    public String getPhn() {
        return phn;
    }
    public void setPhn(String phn) {
        this.phn = phn;
    }


}
