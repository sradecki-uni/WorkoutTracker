package com.example.workouttracker;

import java.util.Date;

public class GlobalWorkoutTableRecord {
    private int id;
    private int workoutTypeId;
    private int weightsId;
    private int weightNameId;
    private int cardioId;
    private int cardioWorkoutId;
    private Date date;
    private int globalTableId;

    public GlobalWorkoutTableRecord(int id, int workoutTypeId, int weightsId, int weightNameId, int cardioId, int cardioWorkoutId, Date date, int globalTableId) {
        this.id = id;
        this.workoutTypeId = workoutTypeId;
        this.weightsId = weightsId;
        this.weightNameId = weightNameId;
        this.cardioId = cardioId;
        this.cardioWorkoutId = cardioWorkoutId;
        this.date = date;
        this.globalTableId = globalTableId;
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

    // setters omitted for brevity, add if needed
}

