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
    private ArrayList<CardioDto> CardioRecordArrayList;
    private Context context;

    // constructor
    public StatsCardioAdapter(ArrayList<CardioDto> CardioRecordArrayList, Context context) {
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
        CardioDto modal = CardioRecordArrayList.get(position);
        holder.cardioName.setText(modal.getName());
        holder.cardioDate.setText(modal.getDate());
        holder.cardioTime.setText(modal.getTime());
        holder.cardioDistance.setText(Float.toString((modal.getDistance())));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return CardioRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        public TextView cardioName,cardioDate, cardioTime, cardioDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            cardioName = (TextView) itemView.findViewById(R.id.idCardioName);
            cardioDate = (TextView) itemView.findViewById(R.id.idCardioDate);
            cardioTime = (TextView) itemView.findViewById(R.id.idCardioTime);
            cardioDistance =(TextView)  itemView.findViewById(R.id.idCardioDistance);
        }
    }
}