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

public class WeightsInput extends AppCompatActivity {

    TextView dateDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    ArrayList<WeightsRecord> weightsWorkout;

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
        weightsWorkout = WeightsRecord.createWeightsWorkout(10);
        // Create adapter and pass workout records
        wAdapter = new WeightsAdapter(weightsWorkout);
        // Attach adapter to the recyclerview to populate
        rvWeights.setAdapter(wAdapter);
        // layout manager position the records
        rvWeights.setLayoutManager(new LinearLayoutManager(this));





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
        weightsWorkout.add(new WeightsRecord());
        // notify the adapter the adapter
        wAdapter.notifyDataSetChanged();

    }

    public void saveWeightsWorkout(View view){
        for(int i = 0; i < weightsWorkout.size(); i++){
            weightsWorkout.get(i);
            // add each record to respective tables
        }

    }
}
