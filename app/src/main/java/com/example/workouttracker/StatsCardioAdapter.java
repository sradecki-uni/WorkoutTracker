package com.example.workouttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatsCardioAdapter extends RecyclerView.Adapter<StatsCardioAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<CardioRecord> CardioRecordArrayList;
    private Context context;

    // constructor
    public StatsCardioAdapter(ArrayList<CardioRecord> CardioRecordArrayList, Context context) {
        this.CardioRecordArrayList =CardioRecordArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_cardio_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        CardioRecord modal = CardioRecordArrayList.get(position);
        System.out.println(modal.getId());
        System.out.println(modal.getmTime());
        System.out.println(modal.getmDistance());
        holder.cardioId.setText(Integer.toString(modal.getId()));
        holder.cardioTime.setText(modal.getmTime());
        holder.cardioDistance.setText(Float.toString((modal.getmDistance())));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return CardioRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        public TextView cardioId, cardioTime, cardioDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            cardioId = (TextView) itemView.findViewById(R.id.idCardioId);
            cardioTime = (TextView) itemView.findViewById(R.id.idCardioTime);
            cardioDistance =(TextView)  itemView.findViewById(R.id.idCardioDistance);
        }
    }
}