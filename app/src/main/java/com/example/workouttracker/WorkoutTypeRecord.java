package com.example.workouttracker;

public class WorkoutTypeRecord {
    private int id;
    private String name;
    private float time;

    public WorkoutTypeRecord(int id, String name, float time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    // method for setting type id, assuming type id is synonymous with id for this class
    public void setTypeId(int id) {
        this.id = id;
    }

    public int getType(){
        return id;
    }
}
