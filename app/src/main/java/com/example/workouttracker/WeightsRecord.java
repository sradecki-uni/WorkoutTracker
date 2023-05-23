package com.example.workouttracker;

public class WeightsRecord {
    private String mType;
    private String mExercise;
    private int mSets, mReps, mId;
    private float mWeight;

    public WeightsRecord(){
        this.mId = 0;
        this.mExercise = "";
        this.mSets = 0;
        this.mReps = 0;
        this.mWeight = 0.0F;
        this.mType = "";
    }

    public WeightsRecord(int id, String exercise){
        this.mId = id;
        this.mExercise = exercise;
        this.mSets = 1;
        this.mReps = 1;
        this.mWeight = 0.0F;
        this.mType = "";
    }

    public WeightsRecord(int id, String exercise, String type, float weight){
        this.mId = id;
        this.mExercise = exercise;
        this.mType = type;
        this.mWeight = weight;
        this.mSets = 1;
        this.mReps = 1;
    }

    public WeightsRecord(int id, String exercise, String type, int sets, int reps, float weight){
        this.mId = id;
        this.mExercise = exercise;
        this.mType = type;
        this.mSets = sets;
        this.mReps = reps;
        this.mWeight = weight;
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

    public void setExercise(String mExercise){
        this.mExercise = mExercise;
    }

    public void setSets(int mSets){
        this.mSets = mSets;
    }

    public void setReps(int mReps){
        this.mReps = mReps;
    }

    public void setWeight(float mWeight){
        this.mWeight = mWeight;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public void setType(String type){mType = type;}

    public boolean isEmpty() {
        return mId == 0 && mExercise.equals("") && mSets == 0 &&
                mReps == 0 && mWeight == 0.0F && mType.equals("");

    }

    public boolean isEnoughToSave() {
        return !(mExercise.equals("") || mSets == 0 ||
                mReps == 0);

    }
}
