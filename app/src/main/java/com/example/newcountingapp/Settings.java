package com.example.newcountingapp;

// this class would configure all the settings attributes for this app
// information from each round would be considered as a settings object containing all the attributes
public class Settings {
    private String button1Name;
    private String button2Name;
    private String button3Name;
    private int maxCount;

    // Constructor
    public Settings(String button1Name, String button2Name, String button3Name, int maxCount) {
        this.button1Name = button1Name;
        this.button2Name = button2Name;
        this.button3Name = button3Name;
        this.maxCount = maxCount;
    }

    // getters and setters
    public String getButton1Name() {
        return button1Name;
    }

    public void setButton1Name(String button1Name) {
        this.button1Name = button1Name;
    }

    public String getButton2Name() {
        return button2Name;
    }

    public void setButton2Name(String button2Name) {
        this.button2Name = button2Name;
    }

    public String getButton3Name() {
        return button3Name;
    }

    public void setButton3Name(String button3Name) {
        this.button3Name = button3Name;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
