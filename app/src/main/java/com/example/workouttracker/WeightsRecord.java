package com.example.workouttracker;

public class WeightsRecord {
    public String mType;
    public String mExercise;
    public int mSets, mReps, mId;
    public float mWeight;
    private int mTypeId;

    public WeightsRecord(){
        mId = 0;
        mExercise = "";
        mSets = 0;
        mReps = 0;
        mWeight = 0.0F;
        mType = "Weights";
    }

    public WeightsRecord(int id, String exercise){
        mId = id;
        mExercise = exercise;
        mSets = 1;
        mReps = 1;
        mWeight = 0.0F;
        mType = "Weights";
    }

    public WeightsRecord(int id, String exercise, String type, float weight){
        mId = id;
        mExercise = exercise;
        mType = type;
        mWeight = weight;
        mSets = 1;
        mReps = 1;
    }

    public WeightsRecord(int id, String exercise, String type, int sets, int reps, float weight){
        mId = id;
        mExercise = exercise;
        mType = type;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public void setTypeId(int typeId) {
        this.mTypeId = typeId;
    }

    // Getter
    public int getTypeId() {
        return this.mTypeId;
    }

    public String getExercise() {
        return mExercise;
    }
    public String getType() {
        return mType;
    }
    public int getId() {
        return mId;
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

    public void setmType(String type){mType = type;}
}
