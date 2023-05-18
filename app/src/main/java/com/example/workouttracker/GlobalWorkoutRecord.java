package com.example.workouttracker;

import java.util.Date;

public class GlobalWorkoutRecord {
    private int id;
    private int exerciseId;
    private String workoutType;
    private Date date;

    public GlobalWorkoutRecord(){

    }
    public GlobalWorkoutRecord(int id, int exerciseId, String workoutType, Date date) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.workoutType = workoutType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
