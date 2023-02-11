package com.example.addhayon.Models;

public class RankModel {
    private String name;
    private int score;
    private int rank;
    private String pImg;
    private String id;
    private String coverImg;
    private String bio;
    private String sclclg;
    private String address;
    private String dateOfbirth;
    private String emailId;
    private String phone;

    public RankModel(String name,int score, int rank,String pImg, String id, String coverImg,String bio, String sclclg, String address, String dateOfbirth,String emailId,String phone) {
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.pImg = pImg;
        this.id = id;
        this.coverImg = coverImg;
        this.bio = bio;
        this.sclclg = sclclg;
        this.address = address;
        this.dateOfbirth = dateOfbirth;
        this.emailId = emailId;
        this.phone = phone;

    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSclclg() {
        return sclclg;
    }

    public void setSclclg(String sclclg) {
        this.sclclg = sclclg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
