package com.example.workouttracker;

import java.util.ArrayList;

public class WeightsRecord {
    public String mExercise, mType;
    public int mSets, mReps;
    public float mWeight;
    static ArrayList<WeightsRecord> mWeightsRecord;

    public WeightsRecord(String exercise, String type){
        mExercise = exercise;
        mType = type;
        mSets = 1;
        mReps = 1;
        mWeight = 0.0F;
    }
    public WeightsRecord(String exercise, String type, int sets, int reps, float weight){
        mExercise = exercise;
        mType = type;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public String getExercise() {
        return mExercise;
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

    public static ArrayList<WeightsRecord> createWeightsWorkout(int numExercises) {
        mWeightsRecord = new ArrayList<WeightsRecord>();

        for (int i = 1; i <= numExercises; i++) {
            mWeightsRecord.add(new WeightsRecord("Ex A " + i, "Ty A " + i));

        }

        return mWeightsRecord;
    }




}
