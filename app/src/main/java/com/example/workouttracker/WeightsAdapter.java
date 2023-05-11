package com.example.workouttracker;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// code for this was sourced from https://guides.codepath.com/android/using-the-recyclerview
public class WeightsAdapter extends
        RecyclerView.Adapter<WeightsAdapter.ViewHolder> {

    public ArrayList<WeightsRecord> mWeightsWorkout;

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

            // use text changed listener to get text from edit text cells

            exerciseView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mWeightsWorkout.get(getAdapterPosition()).
                            setmExercise(exerciseView.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            typeView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mWeightsWorkout.get(getAdapterPosition()).
                            setmType(typeView.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            setsView.addTextChangedListener(new TextWatcher() {
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
                    if (!"".equals(setsView.getText().toString())) {
                        mWeightsWorkout.get(getAdapterPosition()).
                                setmSets(Integer.parseInt(setsView.getText().toString()));

                    }
                }});

            repsView.addTextChangedListener(new TextWatcher() {
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
                    if (!"".equals(repsView.getText().toString())) {
                        mWeightsWorkout.get(getAdapterPosition()).
                                setmReps(Integer.parseInt(repsView.getText().toString()));

                    }
                }
            });

            weightView.addTextChangedListener(new TextWatcher() {
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
                    if (!"".equals(weightView.getText().toString())) {
                        mWeightsWorkout.get(getAdapterPosition()).
                                setmWeight(Float.parseFloat(weightView.getText().toString()));

                    }
                }
            });





        }
    }

    // member var for set of weights records


    // pass weights records array into the constructor
    public WeightsAdapter(ArrayList<WeightsRecord> wRecords) {
        mWeightsWorkout = wRecords;
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

//        EditText exerciseEditView = holder.exerciseView;
//        weightsRecord.setmExercise(exerciseEditView.getText().toString());
//        EditText typeEditView = holder.typeView;
//        weightsRecord.setmType(typeEditView.getText().toString());
//        EditText setsEditView = holder.setsView;
//        weightsRecord.setmSets(Integer.parseInt(setsEditView.getText().toString()));
//        EditText repsEditView = holder.repsView;
//        weightsRecord.setmReps(Integer.parseInt(repsEditView.getText().toString()));
//        EditText weightEditView = holder.weightView;
//        weightsRecord.setmWeight(Float.parseFloat(weightEditView.getText().toString()));

    }

    // total count of items in the list
    @Override
    public int getItemCount() {
        return mWeightsWorkout.size();
    }



}

