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




    public ProfileModel(String name, String email, String bio,String address,String dateofBirth,String sclClg,String phn) {
        this.name = name;
        this.email = email;
        this.bio =bio;
        this.address= address;
        this.dateofBirth=dateofBirth;
        this.sclClg=sclClg;
        this.phn=phn;
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
