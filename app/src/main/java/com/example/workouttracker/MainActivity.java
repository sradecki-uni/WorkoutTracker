package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WorkoutAdapter.OnItemClickListener {
    private ArrayList<WorkoutRecord> workoutArrayList;
    private DBHandler dbHandler;
    private WorkoutAdapter workoutAdapter;
    private RecyclerView homeRV;

    public static final String WORKOUT_ID = "com.example.workouttracker.workoutid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // test code ///////////////////////////////////////
        dbHandler = new DBHandler(this, null, null, 1);
        workoutArrayList = new ArrayList<>();
        workoutArrayList = dbHandler.getAllPreviousWorkouts();
        workoutAdapter = new WorkoutAdapter(workoutArrayList,this,this);
        homeRV = (RecyclerView) findViewById(R.id.idRVHome);
        homeRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // setting our adapter to recycler view.
        homeRV.setAdapter(workoutAdapter);
        // ArrayList<WorkoutRecord> allWorkouts = dbHandler.getAllPreviousWorkouts();
        // get string of the types
        // String allTypes = allWorkouts.get(12).getAllTypes();
        // for choosing the type of intent can use isWeights() or isCardio on the WorkoutRecord
//        boolean cardio = allWorkouts.get(allWorkouts.size() - 1).isCardio();
//        boolean weights = allWorkouts.get(allWorkouts.size() - 2).isWeights();

        // System.out.println(allWorkouts);
        // end test code ///////////////////////////////////////
    }

    public void newWeightsWorkout(View view){
        Intent myIntent = new Intent(this, WeightsInput.class);
        //startActivity(myIntent);
        myIntent.putExtra(WORKOUT_ID, "0");
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

    @Override
    protected void onResume() {
        super.onResume();
        workoutAdapter.updateData();
    }

    @Override
    public void onItemClick(WorkoutRecord wr) {
        if(wr.isCardio()){
            Intent myIntent = new Intent(this, CardioInput.class);
            myIntent.putExtra(WORKOUT_ID,String.valueOf(wr.getmId()));
            startActivity(myIntent);
        }
        else if(wr.isWeights()){
            Intent myIntent = new Intent(this, WeightsInput.class);
            //startActivity(myIntent);
            myIntent.putExtra(WORKOUT_ID,String.valueOf(wr.getmId()));
            startActivity(myIntent);
        }
    }
    public void addWeightsExercise(View view){

    }

    public void findWeightsExercise(View view){

    }

    public void deleteWeightsRecord(View view){

    }
}

