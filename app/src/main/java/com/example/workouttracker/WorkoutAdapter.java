package com.example.workouttracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    public ArrayList<WorkoutRecord> workoutArrayList;
    private Context context;
    private DBHandler dbHandler;
    private final int MAX_ROWS =7;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WorkoutRecord wr);
    }

    public WorkoutAdapter(ArrayList<WorkoutRecord> WorkoutArrayList, OnItemClickListener listener, Context context) {
        this.workoutArrayList = WorkoutArrayList;
        this.listener = listener;
        this.context =context;
        dbHandler = new DBHandler(context, null, null, 1);
    }

    public void updateData(){
        workoutArrayList= dbHandler.getAllPreviousWorkouts();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv, parent, false);
        return new WorkoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        WorkoutRecord modal = workoutArrayList.get(position);
        holder.workoutDate.setText(modal.getmDate());
        holder.workout.setText(modal.getAllTypes());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return Math.min(workoutArrayList.size(), MAX_ROWS);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // creating variables for our text views.
        public TextView workoutDate,workout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            workoutDate = (TextView) itemView.findViewById(R.id.workout_date);
            workout = (TextView) itemView.findViewById(R.id.workout);
            workoutDate.setOnClickListener(this);
            workout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                WorkoutRecord wr = workoutArrayList.get(position);
                listener.onItemClick(wr);
            }
        }
    }

}
