package com.example.workouttracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutRecord {
    public String mDate;
    public Integer mId;
    public ArrayList<CardioRecord> mCardioRecords;
    public ArrayList<WeightsRecord> mWeightsRecords;


    public WorkoutRecord(){
        mId = 0;
        mWeightsRecords = new ArrayList<WeightsRecord>();
        mCardioRecords = new ArrayList<CardioRecord>();
        mDate = "";

    }

    public WorkoutRecord(int id, String date){
        mId = id;
        mWeightsRecords = new ArrayList<WeightsRecord>();
        mCardioRecords = new ArrayList<CardioRecord>();
        mDate = date;
    }

    public void addWeightsRecord(int id, String name, String type, int sets, int reps, float weight){
        mWeightsRecords.add(new WeightsRecord(id, name, type, sets, reps, weight));
    }

    public void addCardioRecord(int id, String exercise, String time){
        mCardioRecords.add(new CardioRecord(id, exercise, time));
    }

    public void addCardioRecord(int id, String exercise, String time, Float distance){
        mCardioRecords.add(new CardioRecord(id, exercise, time, distance));
    }

    public void addCardioRecord(int id, String exercise, Float distance){
        mCardioRecords.add(new CardioRecord(id, exercise, distance));
    }


    public boolean isWeights(){
        return mWeightsRecords.isEmpty();
    }

    public boolean isCardio(){
        return mCardioRecords.isEmpty();
    }

    public String getAllTypes(){
       if(mCardioRecords.isEmpty()){
           // return comma separated string of all unique weights types
           return String.join(", ",
                   mWeightsRecords.stream().map(WeightsRecord::getType).distinct().collect(Collectors.toList()));
       }else if (mWeightsRecords.isEmpty()){
           return "Cardio";
       }
       // if neither weights or cardio records exist return null
       return null;
    }

    public ArrayList<WeightsRecord> getAllWeightsIDs(){
        return mWeightsRecords;
    }

    public ArrayList<CardioRecord> getAllCardioRecords(){
        return mCardioRecords;
    }


    public void setmId(int id){
        mId = id;
    }

    public Integer getmId(){
        return mId;
    }

    public void setmDate(String date){
        mDate = date;
    }

    public String getmDate(){
        return mDate;
    }
}
