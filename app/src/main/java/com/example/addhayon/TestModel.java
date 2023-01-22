package com.example.addhayon;

public class TestModel {
    private String textID;
    private int topScore;
    private int time;

    public TestModel(String textID, int topScore, int time) {
        this.textID = textID;
        this.topScore = topScore;
        this.time = time;
    }

    public String getTextID() {
        return textID;
    }

    public void setTextID(String textID) {
        this.textID = textID;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
