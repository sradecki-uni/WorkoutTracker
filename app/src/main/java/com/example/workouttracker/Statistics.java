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

public class Statistics extends AppCompatActivity{
    TextView dateDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    private ArrayList<CardioDto> cardioModalArrayList;
    private ArrayList<WeightsDto> weightModalArrayList;
    private DBHandler dbHandler;
    private StatsCardioAdapter cardioAdapter;
    private StatsWeightAdapter weightAdapter;
    private RecyclerView cardioRV,weightRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dbHandler = new DBHandler(this, null, null, 1);
        // on create, create predefined type table
        cardioModalArrayList = new ArrayList<>();
        cardioModalArrayList = dbHandler.getLongestCardioSessions();
        weightModalArrayList = new ArrayList<>();
        weightModalArrayList = dbHandler.getHeaviestChestLifts();
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
    }

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
