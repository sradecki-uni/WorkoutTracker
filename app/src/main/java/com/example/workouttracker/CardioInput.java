package com.example.workouttracker;

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

public class CardioInput extends AppCompatActivity {
    TextView dateDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date, workout_date;

    // initialise empty array list
    ArrayList<CardioRecord> cardioWorkout = new ArrayList<CardioRecord>();

    CardioAdapter cAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_input);

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
        RecyclerView rvCardio = (RecyclerView) findViewById(R.id.rv_cardio);

        // Initialize weights records - this will be changed for the database
//        weightsWorkout = WeightsRecord.createWeightsWorkout(10);
        cardioWorkout.add(new CardioRecord());
        // Create adapter and pass workout records
        cAdapter = new CardioAdapter(cardioWorkout);
        // Attach adapter to the recyclerview to populate
        rvCardio.setAdapter(cAdapter);
        // layout manager position the records
        rvCardio.setLayoutManager(new LinearLayoutManager(this));

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

//    public void insertRecord(){
//        int index = weightsWorkout.size();
//        weightsWorkout.add
//    }

    public void addExercise(View view){


        // add new empty record
        //weightsWorkout.add(new WeightsRecord());
        cAdapter.mCardioWorkout.add(new CardioRecord());
        // notify the adapter to show on screen
//        wAdapter.notifyDataSetChanged();
        cAdapter.notifyItemInserted(cAdapter.mCardioWorkout.size());

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
        for(int i = 0; i < cAdapter.mCardioWorkout.size(); i++){
            CardioRecord record = cAdapter.mCardioWorkout.get(i);
            // add record to e_weights and e_exercise table
            dbHandler.addCardio(record);
            dbHandler.addExercise(record);
            // set id in array for each weights records
//            new_weights_id = (weights_id += 1);
            // gat the id of the last weights record added
            int new_cardio_id = dbHandler.getNewestECardioID();
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
            dbHandler.addRelationWorkoutExerciseCardio(workout_id, new_exercise_id, new_cardio_id);

        }

    }
}
