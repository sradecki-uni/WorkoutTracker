package com.example.workouttracker;

public class WeightsNameRecord {
    private int weightId;
    private String name;

    public WeightsNameRecord(int weightId, String name) {
        this.weightId = weightId;
        this.name = name;
    }

    public int getWeightId() {
        return weightId;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

