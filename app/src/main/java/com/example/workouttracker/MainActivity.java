package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // test


    }

    public void newWeightsWorkout(View view){
        Intent myIntent = new Intent(this, WeightsInput.class);

        startActivity(myIntent);
    }

    public void newCardioWorkout(View view){
        Intent myIntent = new Intent(this, CardioInput.class);

        startActivity(myIntent);
    }

    public void addWeightsExercise(View view){

    }

    public void findWeightsExercise(View view){

    }

    public void deleteWeightsRecord(View view){

    }
}
