package com.example.workouttracker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatsWeightAdapter extends RecyclerView.Adapter<StatsWeightAdapter.ViewHolder> {
    private ArrayList<WeightsDto> WeightRecordArrayList;
    private Context context;
    public StatsWeightAdapter(ArrayList<WeightsDto> WeightRecordArrayList, Context context) {
        this.WeightRecordArrayList =WeightRecordArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StatsWeightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_weight_rv, parent, false);
        return new StatsWeightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsWeightAdapter.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        WeightsDto modal = WeightRecordArrayList.get(position);
        holder.name.setText(modal.getName());
        holder.type.setText(modal.getType());
        holder.sets.setText(Integer.toString(modal.getSets()));
        holder.reps.setText(Integer.toString((modal.getReps())));
        holder.weights.setText(Float.toString((modal.getWeight())));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return WeightRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        public TextView  name,type,sets, reps,weights;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            name = (TextView) itemView.findViewById(R.id.idWeightsName);
            type = (TextView) itemView.findViewById(R.id.idWeightsType);
            sets = (TextView) itemView.findViewById(R.id.idSets);
            reps =(TextView)  itemView.findViewById(R.id.idReps);
            weights =(TextView)  itemView.findViewById(R.id.idWeights);
        }
    }

}
