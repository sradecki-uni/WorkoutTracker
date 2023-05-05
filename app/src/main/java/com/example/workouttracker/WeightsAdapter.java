package com.example.workouttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// code for this was sourced from https://guides.codepath.com/android/using-the-recyclerview
public class WeightsAdapter extends
        RecyclerView.Adapter<WeightsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // member variable for any view that will be set as row are rendered
        public EditText exerciseView, typeView, setsView, repsView, weightView;


        // constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Store the itemView in a public member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            exerciseView = (EditText) itemView.findViewById(R.id.w_exercise_input);
            typeView = (EditText) itemView.findViewById(R.id.w_type_input);
            setsView = (EditText) itemView.findViewById(R.id.w_sets_input);
            repsView = (EditText) itemView.findViewById(R.id.w_reps_input);
            weightView = (EditText) itemView.findViewById(R.id.w_weight_input);

        }
    }

    // member var for set of weights records
    private List<WeightsRecord> mWeightsWorkout;

    // pass weights records array into the constructor
    public WeightsAdapter(List<WeightsRecord> contacts) {
        mWeightsWorkout = contacts;
    }

    // inflate weights_workout_rv XML file and return the holder
    @Override
    public WeightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View weightsWorkoutView = inflater.inflate(R.layout.weights_workout_rv, parent, false);

        // new holder instance
        ViewHolder viewHolder = new ViewHolder(weightsWorkoutView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(WeightsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        WeightsRecord weightsRecord = mWeightsWorkout.get(position);

        // Set item views based on your views and data model
        EditText exerciseEditView = holder.exerciseView;
        exerciseEditView.setText(weightsRecord.getExercise());
        EditText typeEditView = holder.typeView;
        typeEditView.setText(weightsRecord.getType());
        EditText setsEditView = holder.setsView;
        setsEditView.setText(Integer.toString(weightsRecord.getSets()));
        EditText repsEditView = holder.repsView;
        repsEditView.setText(Integer.toString(weightsRecord.getReps()));
        EditText weightEditView = holder.weightView;
        weightEditView.setText(Float.toString(weightsRecord.getWeight()));

    }

    // total count of items in the list
    @Override
    public int getItemCount() {
        return mWeightsWorkout.size();
    }

}