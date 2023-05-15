package com.example.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeightsInput extends AppCompatActivity {

    TextView dateDisplay, saveStatus;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date, workout_date;

    // initialise empty array list
    ArrayList<WeightsRecord> weightsWorkout = new ArrayList<WeightsRecord>();

    WeightsAdapter wAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights_input);

        // recieve intent from previous activity
        Intent receiving = getIntent();

        // will implement here an if statemnet checking the intent, weather it comes from
        // new workout button or press on home screen list to see previous workout

        // https://medium.com/@shayma_92409/display-the-current-date-android-studio-f582bf14f908
        // to display the date at the top of the screen
        dateDisplay = (TextView)findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        dateDisplay.setText(date);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // find recyclerview activity layout for this activity
        RecyclerView rvWeights = (RecyclerView) findViewById(R.id.rv_weights);

        // Initialize weights records - this will be changed for the database
//        weightsWorkout = WeightsRecord.createWeightsWorkout(10);
        weightsWorkout.add(new WeightsRecord());
        // Create adapter and pass workout records
        wAdapter = new WeightsAdapter(weightsWorkout);
        // Attach adapter to the recyclerview to populate
        rvWeights.setAdapter(wAdapter);
        // layout manager position the records
        rvWeights.setLayoutManager(new LinearLayoutManager(this));

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        // on create, create predefined type table
        dbHandler.createTypeTable();






    }
    // code for back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void addExercise(View view){


        // add new empty record
        //weightsWorkout.add(new WeightsRecord());
        wAdapter.mWeightsWorkout.add(new WeightsRecord());
        // notify the adapter to show on screen
//        wAdapter.notifyDataSetChanged();
        wAdapter.notifyItemInserted(wAdapter.mWeightsWorkout.size());

    }

    public void saveWeightsWorkout(View view){
        // need to check 1 (version) parameter
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        // get current date and insert e_workout table, recording a new workout
        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        workout_date = date_format.format(date);
        dbHandler.addWorkout(workout_date);

        // get id of the new e_workout record
        int workout_id = dbHandler.getNewestEWorkoutID();

        // for each new record to be saved, iterate through list array and add to e_weights
        // and e_exercise table, note names in e_exercise table are unique so wont get added
        // if have been previously added
        for(int i = 0; i < wAdapter.mWeightsWorkout.size(); i++){
            WeightsRecord record = wAdapter.mWeightsWorkout.get(i);
            // add record to e_weights and e_exercise table
            dbHandler.addWeights(record);
            dbHandler.addExercise(record);
            // set id in array for each weights records
//            new_weights_id = (weights_id += 1);
            // gat the id of the last weights record added
            int new_weights_id = dbHandler.getNewestEWeightsID();
            // get the exercise ID for the exercise in current weight record
            int new_exercise_id = dbHandler.getExerciseID(record);
            // get the type ID for current weight record
            int new_type_id = dbHandler.getTypeID(record);

            // add exercise and type to r_exercise_type, exerciseID fields unique
            // i.e. 1 type can belong to many exercises but 1 exercise can only belong to 1 type
            // 1:N
            dbHandler.addRelationExerciseType(new_type_id, new_exercise_id);

            // add exercise, weights, and workout ID's to r_workout_exercise table
            // N:M
            dbHandler.addRelationWorkoutExerciseWeights(workout_id, new_exercise_id, new_weights_id);

        }

        saveStatus = (TextView) findViewById(R.id.save_status);
        saveStatus.setText(R.string.saved_status_text);
        saveStatus.setTextColor(getColor(R.color.purple_700));

    }
}
