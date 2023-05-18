package com.example.workouttracker;

import java.util.Date;

public class GlobalWorkoutTableRecord {
    private int id;
    private int workoutTypeId;
    private int weightsId;
    private int weightNameId;
    private int cardioId;
    private int cardioWorkoutId;
    private int exerciseTypeId;
    private Date date;
    private int globalTableId;

    public GlobalWorkoutTableRecord(){

    }
    public GlobalWorkoutTableRecord(int id, int workoutTypeId, int weightsId, int weightNameId, int cardioId, int cardioWorkoutId, Date date, int globalTableId, int exerciseTypeId) {
        this.id = id;
        this.workoutTypeId = workoutTypeId;
        this.weightsId = weightsId;
        this.weightNameId = weightNameId;
        this.cardioId = cardioId;
        this.cardioWorkoutId = cardioWorkoutId;
        this.date = date;
        this.globalTableId = globalTableId;
        this.exerciseTypeId = exerciseTypeId;
    }

    public int getId() {
        return id;
    }

    public int getWorkoutTypeId() {
        return workoutTypeId;
    }

    public int getWeightsId() {
        return weightsId;
    }

    public int getWeightNameId() {
        return weightNameId;
    }

    public int getCardioId() {
        return cardioId;
    }

    public int getCardioWorkoutId() {
        return cardioWorkoutId;
    }

    public Date getDate() {
        return date;
    }

    public int getGlobalTableId() {
        return globalTableId;
    }

    public int getExerciseTypeId(){
        return exerciseTypeId;
    }

    // added setters
    public void setId(int id) {
        this.id = id;
    }

    public void setWorkoutTypeId(int workoutTypeId) {
        this.workoutTypeId = workoutTypeId;
    }

    public void setWeightsId(int weightsId) {
        this.weightsId = weightsId;
    }

    public void setWeightNameId(int weightNameId) {
        this.weightNameId = weightNameId;
    }

    public void setCardioId(int cardioId) {
        this.cardioId = cardioId;
    }

    public void setCardioWorkoutId(int cardioWorkoutId) {
        this.cardioWorkoutId = cardioWorkoutId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGlobalTableId(int globalTableId) {
        this.globalTableId = globalTableId;
    }

    public void setExerciseTypeId(int exerciseTypeId){
        this.exerciseTypeId = exerciseTypeId;
    }
}
