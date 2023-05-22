package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String WORKOUT_ID = "com.example.workouttracker.workoutid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // test code ///////////////////////////////////////
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<WorkoutRecord> allWorkouts = dbHandler.getAllPreviousWorkouts();
//        String allTypes = allWorkouts.get(12).getAllTypes();
//        System.out.println(allWorkouts)
        boolean cardio = allWorkouts.get(allWorkouts.size() - 1).isCardio();
        boolean weights = allWorkouts.get(allWorkouts.size() - 2).isWeights();

        System.out.println(allWorkouts);
        // end test code ///////////////////////////////////////
    }

    public void newWeightsWorkout(View view){
        Intent myIntent = new Intent(this, WeightsInput.class);

        startActivity(myIntent);
    }

    public void newCardioWorkout(View view){
        Intent myIntent = new Intent(this, CardioInput.class);
        myIntent.putExtra(WORKOUT_ID, "0");
        startActivity(myIntent);
    }

    public void newStatistics(View view){
        Intent myIntent = new Intent(this, Statistics.class);

        startActivity(myIntent);
    }

    public void addWeightsExercise(View view){

    }

    public void findWeightsExercise(View view){

    }

    public void deleteWeightsRecord(View view){

    }
}

