package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

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
}
