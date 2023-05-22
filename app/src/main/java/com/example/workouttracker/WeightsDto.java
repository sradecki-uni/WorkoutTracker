package com.example.workouttracker;

public class WeightsDto {

    private int sets, reps, id;
    private String type, name;
    private float weight;

    public WeightsDto(int id, String name, String type, int sets, int reps, float weight){
        this.id =id;
        this.name =name;
        this.type =type;
        this.sets =sets;
        this.reps=reps;
        this.weight=weight;
    }
    public int getId(){
        return id;
    }
    public int getSets(){
        return sets;
    }
    public int getReps(){
        return reps;
    }
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public Float getWeight(){
        return weight;
    }
}
