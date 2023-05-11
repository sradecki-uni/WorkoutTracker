package com.example.workouttracker;

import java.util.ArrayList;

public class CardioRecord {
    public String mExercise, mTime, mType;
    public int mId;
    public float mDistance;
    // initialise empty array list


    public CardioRecord(){
        mId = 0;
        mExercise = "";
        mTime = "";
        mDistance = 0.0F;
        mType = "Cardio";
    }

    public CardioRecord(int id, String exercise, String time, Float distance) {
        mId = id;
        mExercise = exercise;
        mTime = time;
        mDistance = distance;
        mType = "Cardio";
    }

    public CardioRecord(int id, String exercise, Float distance) {
        mId = id;
        mExercise = exercise;
        mTime = "";
        mDistance = distance;
        mType = "Cardio";
    }

    public CardioRecord(int id, String exercise, String time) {
        mId = id;
        mExercise = exercise;
        mTime = time;
        mDistance = 0.0F;
        mType = "Cardio";
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

    public String getmType(){
        return mType;
    }
}
