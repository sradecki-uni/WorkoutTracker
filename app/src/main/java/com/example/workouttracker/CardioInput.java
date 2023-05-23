package com.example.workouttracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardioInput extends AppCompatActivity {
    TextView dateDisplay, saveStatus;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date, workout_date;
    Button saveButton, addExerciseButton, deleteButton;
    String workout_id;
    WorkoutRecord previousWorkout;
    RecyclerView rvCardio;

    // initialise empty array list
    ArrayList<CardioRecord> cardioWorkout = new ArrayList<CardioRecord>();

    CardioAdapter cAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_input);

        // recieve intent from previous activity
        Intent receiving = getIntent();
        // get workout id from the home screen
        // if 0 - new workout, not 0 old workout
        workout_id = receiving.getStringExtra(MainActivity.WORKOUT_ID);

        saveStatus = (TextView) findViewById(R.id.save_status_c);
        saveButton = (Button) findViewById(R.id.save_button);
        addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        dateDisplay = (TextView)findViewById(R.id.date_view);
        // hide delete button
        deleteButton.setVisibility(View.INVISIBLE);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // find recyclerview activity layout for this activity
        rvCardio = (RecyclerView) findViewById(R.id.rv_cardio);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        // on create, create predefined type table
        dbHandler.createTypeTable();

        if (workout_id.equals("0")){
            // hide delete button
            deleteButton.setVisibility(View.INVISIBLE);

            // https://medium.com/@shayma_92409/display-the-current-date-android-studio-f582bf14f908
            // to display the date at the top of the screen
            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
            date = dateFormat.format(calendar.getTime());
            dateDisplay.setText(date);



            // Initialize cardio records - this will be changed for the database
            cardioWorkout.add(new CardioRecord());
            // Create adapter and pass workout records
            cAdapter = new CardioAdapter(cardioWorkout);
            // Attach adapter to the recyclerview to populate
            rvCardio.setAdapter(cAdapter);
            // layout manager position the records
            rvCardio.setLayoutManager(new LinearLayoutManager(this));

        } else {
            // show an old workout
            // show delete button
            deleteButton.setVisibility(View.VISIBLE);
            // hide others
            addExerciseButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);

            saveStatus.setText(R.string.saved_status_text);
            saveStatus.setTextColor(getColor(R.color.purple_700));

            previousWorkout = dbHandler.getCardioWorkout(workout_id);

            String prevDate = previousWorkout.getmDate();

            // https://medium.com/@shayma_92409/display-the-current-date-android-studio-f582bf14f908
            // to display the date at the top of the screen
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date temp = dateFormat.parse(prevDate);
                dateFormat.applyPattern("EEE, MMM d, yyyy");
                date = dateFormat.format(temp);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            dateDisplay.setText(date);
            // get the weights records
            cardioWorkout = previousWorkout.getAllCardioRecords();
            // Create adapter and pass workout records
            cAdapter = new CardioAdapter(cardioWorkout);
            // Attach adapter to the recyclerview to populate
            rvCardio.setAdapter(cAdapter);
            // layout manager position the records
            rvCardio.setLayoutManager(new LinearLayoutManager(this));
        }





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

        // scroll to bottom when new exercise inserted
        rvCardio.scrollToPosition(cAdapter.mCardioWorkout.size() - 1);

    }

    public void saveCardioWorkout(View view){
        // check each record to save that enough data has been input
        for(int i = 0; i < cAdapter.mCardioWorkout.size(); i++){
            // check at least name AND (time or distance) and that time is valid
            if (!cAdapter.mCardioWorkout.get(i).isEnoughToSave()){
                saveStatus.setText(R.string.fill_saved_status_text_c);
                saveStatus.setTextColor(getColor(R.color.red));
                return;
            }
            // time can be empty if distance is input, but if not empty check its valid
            if(!cAdapter.mCardioWorkout.get(i).isValidTime() && !cAdapter.mCardioWorkout.get(i).getTime().equals("")){
                saveStatus.setText(R.string.invalid_time);
                saveStatus.setTextColor(getColor(R.color.red));
                return;
            }
        }
        // remove add and save buttons
        saveButton.setVisibility(View.INVISIBLE);
        addExerciseButton.setVisibility(View.INVISIBLE);
        // show delete button
        deleteButton.setVisibility(View.VISIBLE);
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

            // gat the id of the last weights record added
            int new_cardio_id = dbHandler.getNewestECardioID();
            // get the exercise ID for the exercise in current weight record
            int new_exercise_id = dbHandler.getExerciseID(record);
            // get the type ID for current cardio record
            int new_type_id = dbHandler.getTypeID(record);

            // add exercise and type to r_exercise_type, exerciseID fields unique
            // i.e. 1 type can belong to many exercises but 1 exercise can only belong to 1 type
            // 1:N
            dbHandler.addRelationExerciseType(new_type_id, new_exercise_id);

            // add exercise, weights, and workout ID's to r_workout_exercise table
            // N:M
            dbHandler.addRelationWorkoutExerciseCardio(workout_id, new_exercise_id, new_cardio_id);

        }

        saveStatus = (TextView) findViewById(R.id.save_status_c);
        saveStatus.setText(R.string.saved_status_text);
        saveStatus.setTextColor(getColor(R.color.purple_700));

        cAdapter.notifyDataSetChanged();

    }

    public void deleteCardioWorkout(View view){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        // deleting the workout that was just saved
        if(workout_id.equals("0")){
            // get newest workout id, i.e. this workout
            String id = String.valueOf(dbHandler.getNewestEWorkoutID());
            dbHandler.DeleteCardioWorkout(id);
            // return to home screen
            this.finish();
        } else {
            // delete the workout id that was passed as an intent
            dbHandler.DeleteCardioWorkout(workout_id);
            // return to home screen
            this.finish();
        }
    }



}
