package com.example.workouttracker;

import java.util.ArrayList;

public class WeightsRecord {
    public String mExercise, mType;
    public int mSets, mReps, mId;
    public float mWeight;
    // initialise empty array list
    static ArrayList<WeightsRecord> mWeightsRecord = new ArrayList<WeightsRecord>();

    public WeightsRecord(){
        mId = 0;
        mExercise = "";
        mType = "";
        mSets = 0;
        mReps = 0;
        mWeight = 0.0F;
    }

    public WeightsRecord(int id, String exercise, String type){
        mId = id;
        mExercise = exercise;
        mType = type;
        mSets = 1;
        mReps = 1;
        mWeight = 0.0F;
    }
    public WeightsRecord(int id, String exercise, String type, int sets, int reps, float weight){
        mId = id;
        mExercise = exercise;
        mType = type;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public String getExercise() {
        return mExercise;
    }
    public int getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public int getSets() {
        return mSets;
    }

    public int getReps() {
        return mReps;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setmExercise(String mExercise){
        this.mExercise = mExercise;
    }

    public void setmType(String mType){
        this.mType = mType;
    }

    public void setmSets(int mSets){
        this.mSets = mSets;
    }

    public void setmReps(int mReps){
        this.mReps = mReps;
    }

    public void setmWeight(float mWeight){
        this.mWeight = mWeight;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

//    public static ArrayList<WeightsRecord> createWeightsWorkout(int numExercises) {
//        mWeightsRecord = new ArrayList<WeightsRecord>();
//
//        for (int i = 1; i <= numExercises; i++) {
//            mWeightsRecord.add(new WeightsRecord(i, "Ex A " + i, "Ty A " + i));
//
//        }
//
//        return mWeightsRecord;
//    }




}
