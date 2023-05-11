package com.example.workouttracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// code for this was sourced from https://guides.codepath.com/android/using-the-recyclerview
public class CardioAdapter extends
        RecyclerView.Adapter<CardioAdapter.ViewHolder> {

    public ArrayList<CardioRecord> mCardioWorkout;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // member variable for any view that will be set as row are rendered
        public EditText exerciseView, timeView, distanceView;


        // constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Store the itemView in a public member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            exerciseView = (EditText) itemView.findViewById(R.id.c_exercise_input);
            timeView = (EditText) itemView.findViewById(R.id.c_time_input);
            distanceView = (EditText) itemView.findViewById(R.id.c_distance_input);

            // use text changed listener to get text from edit text cells

            exerciseView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCardioWorkout.get(getAdapterPosition()).
                            setmExercise(exerciseView.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            timeView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCardioWorkout.get(getAdapterPosition()).
                            setmTime(timeView.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            distanceView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // if string not empty, get the text from the view and save in to
                    // weights record object
                    if (!"".equals(distanceView.getText().toString())) {
                        mCardioWorkout.get(getAdapterPosition()).
                                setmDistance(Float.parseFloat(distanceView.getText().toString()));

                    }
                }
            });





        }
    }

    // member var for set of weights records


    // pass weights records array into the constructor
    public CardioAdapter(ArrayList<CardioRecord> cRecords) {
        mCardioWorkout = cRecords;
    }

    // inflate cardio_workout_rv XML file and return the holder
    @Override
    public CardioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cardioWorkoutView = inflater.inflate(R.layout.cardio_workout_rv, parent, false);

        // new holder instance
        ViewHolder viewHolder = new ViewHolder(cardioWorkoutView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CardioAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        CardioRecord cardioRecord = mCardioWorkout.get(position);

        // Set item views based on your views and data model
        EditText exerciseEditView = holder.exerciseView;
        exerciseEditView.setText(cardioRecord.getExercise());
        EditText timeEditView = holder.timeView;
        timeEditView.setText(cardioRecord.getmTime());
        EditText distanceEditView = holder.distanceView;
        distanceEditView.setText(Float.toString(cardioRecord.getmDistance()));


    }

    // total count of items in the list
    @Override
    public int getItemCount() {
        return mCardioWorkout.size();
    }



}
