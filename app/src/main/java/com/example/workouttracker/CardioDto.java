package com.example.workouttracker;

public class CardioDto {

    private int id;
    private String name, date, time;
    private Float distance;
    public CardioDto(int id, String name,String date, String time, Float distance) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.distance = distance;
    }

    int getId(){
        return id;
    }

    String getName(){
        return name;
    }
    String getDate(){
        return date;
    }
    String getTime(){
        return time;
    }
    Float getDistance(){
        return distance;
    }

}
