package com.example.workouttracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardioRecord {
    public static final String mType = "Cardio";
    public String mExercise, mTime;
    public int mId;
    public float mDistance;

    public CardioRecord(){
        mId = 0;
        mExercise = "";
        mTime = "";
        mDistance = 0.0F;
    }

    public CardioRecord(int id, String exercise, String time, Float distance) {
        mId = id;
        mExercise = exercise;
        mTime = time;
        mDistance = distance;
    }

    public CardioRecord(int id, String exercise, Float distance) {
        mId = id;
        mExercise = exercise;
        mTime = "";
        mDistance = distance;
    }

    public CardioRecord(int id, String exercise, String time) {
        mId = id;
        mExercise = exercise;
        mTime = time;
        mDistance = 0.0F;
    }

    public String getExercise() {
        return mExercise;
    }
    public int getId() {
        return mId;
    }

    public String getmTime() {
        return mTime;
    }

    public String getType() {
        return mType;
    }

    public float getmDistance() {
        return mDistance;
    }

    public void setmExercise(String exercise){
        this.mExercise = exercise;
    }

    public void setmTime(String time){
        this.mTime = time;
    }

    public void setmDistance(float distance){
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

    // (HH:MM:SS  or HH:MM)
    public boolean isValidTime()
    {
        // Regex to check valid Time - range 0:0:0 to 23:59:59
        String regex
                = "^(?:[01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]$";

        // Compile the ReGex
        Pattern pattern = Pattern.compile(regex);

        //The string can be empty so dont need to check

        // Pattern class contains matcher()
        // method to find matching between given str using regex.
        Matcher m = pattern.matcher(mTime);

        // Return if the str
        // matched the ReGex
        return m.matches();
    }
}
