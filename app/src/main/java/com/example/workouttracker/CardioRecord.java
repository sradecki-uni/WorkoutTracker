package com.example.workouttracker;

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
}
