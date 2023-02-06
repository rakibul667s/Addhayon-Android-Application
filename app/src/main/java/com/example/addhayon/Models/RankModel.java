package com.example.addhayon.Models;

public class RankModel {
    private String name;
    private int score;
    private int rank;
    private String pImg;

    public RankModel(String name,int score, int rank,String pImg) {
        this.score = score;
        this.rank = rank;
        this.name = name;
        this.pImg = pImg;
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
