package com.example.workouttracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardioRecord {
    private static final String mType = "Cardio";
    private String mExercise, mTime, mDate;
    private int mId;
    private float mDistance;


    public CardioRecord(){
        this.mId = 0;
        this.mExercise = "";
        this.mTime = "";
        this.mDistance = 0.0F;
        this.mDate = "";
    }

    public CardioRecord(int id, String exercise, String time, Float distance) {
        this.mId = id;
        this.mExercise = exercise;
        this.mTime = time;
        this.mDistance = distance;
        this.mDate = "";
    }

    public CardioRecord(int id, String exercise, Float distance) {
        this.mId = id;
        this.mExercise = exercise;
        this.mTime = "";
        this.mDistance = distance;
        this.mDate = "";
    }

    public CardioRecord(int id, String exercise, String time) {
        this.mId = id;
        this.mExercise = exercise;
        this.mTime = time;
        this.mDistance = 0.0F;
        this.mDate = "";
    }

    public CardioRecord(int id, String exercise, String date, String time, Float distance) {
        this.mId = id;
        this.mExercise = exercise;
        this.mDate = date;
        this.mTime = time;
        this.mDistance = distance;
    }

    public String getExercise() { return mExercise; }
    public String getDate() { return mDate; }
    public int getId() {
        return mId;
    }

    public String getTime() {
        return mTime;
    }

    public String getType() {
        return mType;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setExercise(String exercise){
        this.mExercise = exercise;
    }

    public void setDate(String date){
        this.mDate = date;
    }

    public void setTime(String time){
        this.mTime = time;
    }

    public void setDistance(float distance){
        this.mDistance = distance;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public boolean isEmpty() {
        return mId == 0 && mExercise.equals("") &&
                mDistance == 0.0F && mTime.equals("");

    }

    public boolean isEnoughToSave() {
        return !mExercise.equals("") && (!mTime.equals("") ||
                mDistance != 0.0F);

    }

    // code sourced from https://www.geeksforgeeks.org/validating-traditional-time-formats-using-regular-expression/
    public boolean isValidTime()
    {
        // Regex to check valid Time - range 0:0:0 to 23:59:59
        String regex
                = "^(?:[01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]$";

        // Compile regex
        Pattern pattern = Pattern.compile(regex);

        // method to find matching between given str using regex.
        Matcher m = pattern.matcher(mTime);

        // Return if time matched the ReGex
        return m.matches();
    }
}
