package com.example.workouttracker;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// code for this was sourced from https://guides.codepath.com/android/using-the-recyclerview
public class WeightsAdapter extends
        RecyclerView.Adapter<WeightsAdapter.ViewHolder> {

    public ArrayList<WeightsRecord> mWeightsWorkout;
    public ArrayAdapter<CharSequence>adapter;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // member variable for any view that will be set as row are rendered
        public EditText exerciseView, setsView, repsView, weightView;
        public Spinner typeSpinner;




        // constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Store the itemView in a public member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            exerciseView = (EditText) itemView.findViewById(R.id.w_exercise_input);
            typeSpinner = (Spinner) itemView.findViewById(R.id.spinner_type);
            setsView = (EditText) itemView.findViewById(R.id.w_sets_input);
            repsView = (EditText) itemView.findViewById(R.id.w_reps_input);
            weightView = (EditText) itemView.findViewById(R.id.w_weight_input);

            // set adapter for the dropdown menus
            adapter = ArrayAdapter.createFromResource(typeSpinner.getContext(), R.array.type_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            typeSpinner.setAdapter(adapter);

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


            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        mWeightsWorkout.get(getAdapterPosition()).setmType(typeSpinner.getSelectedItem().toString());
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {


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
                }
            });

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
//        EditText typeEditView = holder.typeView;
//        typeEditView.setText(weightsRecord.getType());
        Spinner typeSpinnerView = holder.typeSpinner;
        // if not a new workout find the type to display
        if (weightsRecord.getType() != "") {
            int spinnerPosition = adapter.getPosition(weightsRecord.getType());
            typeSpinnerView.setSelection(spinnerPosition);
        }
        EditText setsEditView = holder.setsView;
        setsEditView.setText(Integer.toString(weightsRecord.getSets()));
        EditText repsEditView = holder.repsView;
        repsEditView.setText(Integer.toString(weightsRecord.getReps()));
        EditText weightEditView = holder.weightView;
        weightEditView.setText(Float.toString(weightsRecord.getWeight()));

        if(!weightsRecord.isEmpty()){
            exerciseEditView.setFocusable(false);
            typeSpinnerView.setEnabled(false);
            setsEditView.setFocusable(false);
            repsEditView.setFocusable(false);
            weightEditView.setFocusable(false);
        }

    }

    // total count of items in the list
    @Override
    public int getItemCount() {
        return mWeightsWorkout.size();
    }



}

