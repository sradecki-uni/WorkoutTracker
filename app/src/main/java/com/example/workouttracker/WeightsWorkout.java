package com.example.workouttracker;

import java.util.ArrayList;


// might get rid of this class
public class WeightsWorkout {
    public String mDate;
    static ArrayList<WeightsRecord> mWeightsRecord;

    public WeightsWorkout(String date){
        mDate = date;
    }

    public static ArrayList<WeightsRecord> createWeightsWorkout(int numExercises) {
        mWeightsRecord = new ArrayList<WeightsRecord>();

        for (int i = 1; i <= numExercises; i++) {
            mWeightsRecord.add(new WeightsRecord("Ex A " + i, "Ty A " + i));

        }

        return mWeightsRecord;
    }
}
