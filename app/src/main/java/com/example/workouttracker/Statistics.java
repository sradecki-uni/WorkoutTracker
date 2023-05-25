package com.example.workouttracker;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class Statistics extends AppCompatActivity{
    TextView dateDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    private ArrayList<CardioRecord> cardioModalArrayList;
    private ArrayList<WeightsRecord> weightModalArrayList;
    private DBHandler dbHandler;
    private StatsCardioAdapter cardioAdapter;
    private StatsWeightAdapter weightAdapter;
    private RecyclerView cardioRV,weightRV;

    private Spinner typeSpinner;
    public ArrayAdapter<CharSequence> adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dbHandler = new DBHandler(this, null, null, 1);
        // on create, create predefined type table
        cardioModalArrayList = new ArrayList<>();
        cardioModalArrayList = dbHandler.getLongestCardioSessions();
        weightModalArrayList = new ArrayList<>();
        weightModalArrayList = dbHandler.getHeaviestLifts("Abs");
        // receive intent from previous activity
        Intent receiving = getIntent();
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        dateDisplay = (TextView)findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        dateDisplay.setText(date);
        cardioAdapter = new StatsCardioAdapter(cardioModalArrayList, this);
        cardioRV = (RecyclerView) findViewById(R.id.idRVCardio);
        cardioRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // setting our adapter to recycler view.
        cardioRV.setAdapter(cardioAdapter);
        weightAdapter = new StatsWeightAdapter(weightModalArrayList,this);
        weightRV = (RecyclerView) findViewById(R.id.idRVWeights);
        weightRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // setting our adapter to recycler view.
        weightRV.setAdapter(weightAdapter);

        // type spinner for type select
        typeSpinner = (Spinner) findViewById(R.id.spinner_type_stats);
        // set adapter for the dropdown menus
        adapter = ArrayAdapter.createFromResource(typeSpinner.getContext(), R.array.type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(adapter);


        // when the slelected type changes update the list of weights records and notify the adapter
        // to update the view
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    weightModalArrayList.clear();
                    weightModalArrayList.addAll(dbHandler.getHeaviestLifts(typeSpinner.getSelectedItem().toString()));
                    weightAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

    }

    // code for back button sourced from https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
