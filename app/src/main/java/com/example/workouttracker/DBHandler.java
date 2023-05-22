package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;
import java.util.ArrayList;

import androidx.annotation.Nullable;
// import

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDB.db";
    private static final String TABLE_WORKOUT = "e_workout";
    private static final String TABLE_TYPE = "e_type";
    private static final String TABLE_EXERCISE = "e_exercise";
    private static final String TABLE_WEIGHTS = "e_weights";
    private static final String TABLE_CARDIO = "e_cardio";

    private static final String TABLE_EXERCISE_TYPE = "r_exercise_type";

    private static final String TABLE_WORKOUT_EXERCISE = "r_workout_exercise";

    // used for name field in e_type, e_exercise tables
    public static final String COLUMN_NAME = "name";

    // e_weight record fields
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";

    // e_cardio record fields
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DISTANCE = "distance";


    // used for id field in all entity tables
    public static final String COLUMN_ID = "id";


    // e_weight record fields
    public static final String COLUMN_DATE = "date";

    // r_exercise_type record fields
    public static final String COLUMN_TYPEID = "typeID";
    // used in r_workout_exercise too
    public static final String COLUMN_EXERCISID = "exerciseID";

    // other r_workout_exercise fields
    public static final String COLUMN_WORKOUTID = "workoutID";
    public static final String COLUMN_WEIGHTSID = "weightsID";
    public static final String COLUMN_CARDIOID = "cardioID";



    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_E_TYPE_TABLE = "CREATE TABLE e_type (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "name TEXT NOT NULL UNIQUE ON CONFLICT IGNORE);";

        String CREATE_E_EXERCISE_TABLE = "CREATE TABLE e_exercise (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ON CONFLICT IGNORE, " +
                "name TEXT NOT NULL UNIQUE ON CONFLICT IGNORE);";

        String CREATE_E_WORKOUT_TABLE = "CREATE TABLE e_workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "date TEXT NOT NULL);";

        String CREATE_E_WEIGHTS_TABLE = "CREATE TABLE e_weights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "sets UNSIGNED INTEGER DEFAULT 0, " +
                "reps UNSIGNED INTEGER DEFAULT 0, " +
                "weight UNSIGNED FLOAT DEFAULT 0.0 );";

        String CREATE_E_CARDIO_TABLE = "CREATE TABLE e_cardio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "time string DEFAULT '00:00:00', " +
                "distance UNSIGNED FLOAT DEFAULT 0.0 );";

        String CREATE_R_EXERCISE_TYPE_TABLE = "CREATE TABLE r_exercise_type (" +
                "exerciseID INTEGER UNIQUE ON CONFLICT IGNORE, " +
                "typeID INTEGER);";

        String CREATE_R_WORKOUT_EXERCISE_TABLE = "CREATE TABLE r_workout_exercise (" +
                "workoutID INTEGER, " +
                "exerciseID INTEGER, " +
                "cardioID INTEGER, " +
                "weightsID INTEGER);" ;

        db.execSQL(CREATE_E_TYPE_TABLE);
        db.execSQL(CREATE_E_EXERCISE_TABLE);
        db.execSQL(CREATE_E_WEIGHTS_TABLE);
        db.execSQL(CREATE_E_CARDIO_TABLE);
        db.execSQL(CREATE_E_WORKOUT_TABLE);
        db.execSQL(CREATE_R_EXERCISE_TYPE_TABLE);
        db.execSQL(CREATE_R_WORKOUT_EXERCISE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // might not need to recreate type table as it will be predefined
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        // will probably need to do the same for relational tables
        onCreate(db);

    }
    public void createTypeTable(){
        SQLiteDatabase db_write = this.getWritableDatabase();

        // create predefined types for type tables
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Chest");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Arms");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Abs");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Shoulders");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Legs");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Cardio");
        db_write.insert(TABLE_TYPE, null, values);
        values.put(COLUMN_NAME, "Back");
        db_write.insert(TABLE_TYPE, null, values);
        db_write.close();
    }
    public void addWeights( WeightsRecord wr){
        ContentValues values_weights = new ContentValues();
        values_weights.put(COLUMN_REPS, wr.getReps());
        values_weights.put(COLUMN_WEIGHT, wr.getWeight());
        values_weights.put(COLUMN_SETS, wr.getSets());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_WEIGHTS, null, values_weights);
        db.close();
    }

    public void addCardio( CardioRecord cr){
        ContentValues values_weights = new ContentValues();
        values_weights.put(COLUMN_TIME, cr.getmTime());
        values_weights.put(COLUMN_DISTANCE, cr.getmDistance());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CARDIO, null, values_weights);
        db.close();
    }

    public void addExercise( WeightsRecord wr){
        ContentValues values_exercise = new ContentValues();
        values_exercise.put(COLUMN_NAME, wr.getExercise());

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.insertOrThrow(TABLE_EXERCISE, null, values_exercise);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }



        db.close();
    }

    public void addExercise( CardioRecord cr){
        ContentValues values_exercise = new ContentValues();
        values_exercise.put(COLUMN_NAME, cr.getExercise());

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.insertOrThrow(TABLE_EXERCISE, null, values_exercise);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }



        db.close();
    }

    public void addWorkout(String date){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.insert(TABLE_WORKOUT, null, values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }


        db.close();
    }

    public int getNewestEWorkoutID(){
        int id;
        try {
//            String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_WORKOUT ;
            String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_WORKOUT ;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }

        return id;
    }

    public int getNewestEWeightsID(){
        int id;
        try {
            String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_WEIGHTS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public int getNewestECardioID(){
        int id;
        try {
            String query = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_CARDIO;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public int getExerciseID(WeightsRecord wr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_EXERCISE + " WHERE "
                    + COLUMN_NAME + " = '"  + wr.getExercise() + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public int getExerciseID(CardioRecord cr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_EXERCISE + " WHERE "
                    + COLUMN_NAME + " = '"  + cr.getExercise() + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public int getTypeID(WeightsRecord wr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_TYPE + " WHERE "
                    + COLUMN_NAME + " = '"  + wr.getType() + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public int getTypeID(CardioRecord cr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_TYPE + " WHERE "
                    + COLUMN_NAME + " = '"  + cr.getType() + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            // if no entries are in the database
            id = 1;
        }
        return id;
    }

    public void addRelationExerciseType(int typeID, int exerciseID){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISID, exerciseID);
        values.put(COLUMN_TYPEID, typeID);

        // Insert the new row, returning the primary key value of the new row
        try {
            db.insertOrThrow(TABLE_EXERCISE_TYPE,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }



        db.close();
    }

    public void addRelationWorkoutExerciseWeights(int workoutID, int exerciseID, int weightsID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISID, exerciseID);
        values.put(COLUMN_WORKOUTID, workoutID);
        values.put(COLUMN_WEIGHTSID, weightsID);

        try {
            db.insertOrThrow(TABLE_WORKOUT_EXERCISE,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }



        db.close();
    }

    public void addRelationWorkoutExerciseCardio(int workoutID, int exerciseID, int cardioID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISID, exerciseID);
        values.put(COLUMN_WORKOUTID, workoutID);
        values.put(COLUMN_CARDIOID, cardioID);

        try {
            db.insertOrThrow(TABLE_WORKOUT_EXERCISE,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }



        db.close();
    }

    // might need to adjust this to qurey the r_workout_exercise table
    public WeightsRecord findWeightsExercise(String exercise){
        String query = "SELECT * FROM" + TABLE_WORKOUT + "WHERE " + COLUMN_NAME + " = \"" + exercise + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        WeightsRecord wr = new WeightsRecord();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            wr.setId(cursor.getInt(0));
            wr.setmExercise(cursor.getString(1));
            wr.setmType(cursor.getString(2));
            wr.setmSets(Integer.parseInt(cursor.getString(3)));
            wr.setmReps(Integer.parseInt(cursor.getString(4)));
            wr.setmWeight(Integer.parseInt(cursor.getString(5)));


        }
        else{
            wr = null;
        }
        db.close();
        return wr;

    }

    // might need to adjust this to query the r_workout_exercise table
    public boolean deleteWeightsRecord(String mWeight){
        boolean result = false;

        String query = "Select * From " + TABLE_WORKOUT + "WHERE " + COLUMN_NAME + " = \"" + mWeight + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        WeightsRecord wr = new WeightsRecord();

        if(cursor.moveToFirst()){
            wr.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_WORKOUT,  COLUMN_NAME + " = ?",
                    new String [] {String.valueOf(wr.getId())});
            cursor.close();
            result = true;

        }
        db.close();
        return result;

    }

    public ArrayList<CardioDto> getLongestCardioSessions(){
        SQLiteDatabase db = this.getReadableDatabase();
        String cardioQuery = "SELECT " +
                TABLE_CARDIO + "." + COLUMN_ID + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_WORKOUT + "." + COLUMN_DATE + ", " +
                TABLE_CARDIO + "." + COLUMN_TIME + ", " +
                TABLE_CARDIO + "." + COLUMN_DISTANCE  + " FROM " + TABLE_CARDIO +","
                + TABLE_WORKOUT +","+ TABLE_EXERCISE + "," + TABLE_WORKOUT_EXERCISE +
                " WHERE " + TABLE_CARDIO +"."+ COLUMN_ID +"="+ TABLE_WORKOUT_EXERCISE +"."+ COLUMN_CARDIOID +
                " AND " + TABLE_WORKOUT +"."+ COLUMN_ID +"=" + TABLE_WORKOUT_EXERCISE +"."+ COLUMN_WORKOUTID +
                " AND " + TABLE_EXERCISE +"."+ COLUMN_ID +"=" + TABLE_WORKOUT_EXERCISE +"."+ COLUMN_EXERCISID +
                " ORDER BY " + TABLE_CARDIO + "." + COLUMN_TIME + " DESC LIMIT 3";
        Cursor cardioRecords = db.rawQuery(cardioQuery,null);
        ArrayList<CardioDto> cardioRecordsArrayList = new ArrayList<>();
        if (cardioRecords.moveToFirst()) {
            do {
                cardioRecordsArrayList.add(new CardioDto(
                        cardioRecords.getInt(0),
                        cardioRecords.getString(1),
                        cardioRecords.getString(2),
                        cardioRecords.getString(3),
                        cardioRecords.getFloat(4)));
            } while (cardioRecords.moveToNext());
        }
        cardioRecords.close();
        return cardioRecordsArrayList;
    }

    public ArrayList<WorkoutRecord> getAllPreviousWorkouts(){
        ArrayList<WorkoutRecord> allWorkouts = new ArrayList<WorkoutRecord>();
        WorkoutRecord currentWorkoutRecord;
        int lastWorkoutID = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        // returns format: workoutID | date | Exercise name | Time | Distance | type
        String cardioQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_WORKOUT + "." + COLUMN_DATE + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_CARDIO + "." + COLUMN_TIME + ", " +
                TABLE_CARDIO + "." + COLUMN_DISTANCE + ", " +
                TABLE_TYPE + "." + COLUMN_NAME +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_EXERCISE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_EXERCISE_TYPE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_TYPE + " ON " +
                TABLE_TYPE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_TYPEID +
                " JOIN " + TABLE_CARDIO + " ON " +
                TABLE_CARDIO + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_CARDIOID;

        // returns format: workoutID | date | Exercise name | sets | reps | weight | type
        String weightsQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_WORKOUT + "." + COLUMN_DATE + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_WEIGHTS + "." + COLUMN_SETS + ", " +
                TABLE_WEIGHTS + "." + COLUMN_REPS + ", " +
                TABLE_WEIGHTS + "." + COLUMN_WEIGHT + ", " +
                TABLE_TYPE + "." + COLUMN_NAME +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_EXERCISE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_EXERCISE_TYPE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_TYPE + " ON " +
                TABLE_TYPE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_TYPEID +
                " JOIN " + TABLE_WEIGHTS + " ON " +
                TABLE_WEIGHTS + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WEIGHTSID;

        // get any cardio records
        Cursor workoutRecords_cardio_cursor = db.rawQuery(cardioQuery, null);
        if(workoutRecords_cardio_cursor.moveToFirst()){
            do {


                // if current workout record id different from the last, add new workout record
                if ( lastWorkoutID != workoutRecords_cardio_cursor.getInt(0)){
                    allWorkouts.add(new WorkoutRecord());
                }
                // get the current workout record
                currentWorkoutRecord = allWorkouts.get(allWorkouts.size()-1);
                currentWorkoutRecord.setmId(workoutRecords_cardio_cursor.getInt(0));
                currentWorkoutRecord.setmDate(workoutRecords_cardio_cursor.getString(1));
                currentWorkoutRecord.addCardioRecord(
                        // set id for cardio record to 0,
                        // as it doesnt necessarily matter when getting records from database
                        0,
                        // get name
                        workoutRecords_cardio_cursor.getString(2),
                        // get time
                        workoutRecords_cardio_cursor.getString(3),
                        // get distance
                        workoutRecords_cardio_cursor.getFloat(4)
                );
                // get id of current workout workout for next record
                lastWorkoutID = workoutRecords_cardio_cursor.getInt(0);
            }
            while (workoutRecords_cardio_cursor.moveToNext());
        }

        // get any weights records
        Cursor workoutRecords_weights_cursor = db.rawQuery(weightsQuery, null);
        // reset last workout id
        lastWorkoutID = 0;
        if(workoutRecords_weights_cursor.moveToFirst()){
            do {

                // if current workout record id different from the last, add new workout record
                if ( lastWorkoutID != workoutRecords_weights_cursor.getInt(0)){
                    allWorkouts.add(new WorkoutRecord());
                }
                // get the current workout record
                currentWorkoutRecord = allWorkouts.get(allWorkouts.size()-1);
                currentWorkoutRecord.setmId(workoutRecords_weights_cursor.getInt(0));
                currentWorkoutRecord.setmDate(workoutRecords_weights_cursor.getString(1));
                currentWorkoutRecord.addWeightsRecord(
                        // set id for weigts record to 0
                        0,
                        // get name
                        workoutRecords_weights_cursor.getString(2),
                        // get type
                        workoutRecords_weights_cursor.getString(6),
                        // get sets
                        workoutRecords_weights_cursor.getInt(3),
                        // get reps
                        workoutRecords_weights_cursor.getInt(4),
                        // get weight
                        workoutRecords_weights_cursor.getFloat(5)
                );
                // get id of last workout
                lastWorkoutID = workoutRecords_weights_cursor.getInt(0);
            }
            while (workoutRecords_weights_cursor.moveToNext());
        }

        // code sourced from https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/
        // sort all workouts by ID (Newest ID first),
        // with the newest first (i.e. reverse order of IDs)
        allWorkouts.sort(Comparator.comparing(WorkoutRecord::getmId).reversed());

        return allWorkouts;
    }


    public WorkoutRecord getWeightsWorkout(String id){
        WorkoutRecord workoutRecord = new WorkoutRecord();

        SQLiteDatabase db = this.getReadableDatabase();

        // returns format: workoutID | date | Exercise name | sets | reps | weight | type
        String weightsQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_WORKOUT + "." + COLUMN_DATE + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_WEIGHTS + "." + COLUMN_SETS + ", " +
                TABLE_WEIGHTS + "." + COLUMN_REPS + ", " +
                TABLE_WEIGHTS + "." + COLUMN_WEIGHT + ", " +
                TABLE_TYPE + "." + COLUMN_NAME +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_EXERCISE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_EXERCISE_TYPE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_TYPE + " ON " +
                TABLE_TYPE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_TYPEID +
                " JOIN " + TABLE_WEIGHTS + " ON " +
                TABLE_WEIGHTS + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WEIGHTSID +
                " WHERE " + TABLE_WORKOUT + "." + COLUMN_ID + " == " + id;


        // get any weights records
        Cursor workoutRecords_weights_cursor = db.rawQuery(weightsQuery, null);

        if(workoutRecords_weights_cursor.moveToFirst()){
            do {
                workoutRecord.setmId(workoutRecords_weights_cursor.getInt(0));
                workoutRecord.setmDate(workoutRecords_weights_cursor.getString(1));
                workoutRecord.addWeightsRecord(
                        // set id for weigts record to 0
                        0,
                        // get name
                        workoutRecords_weights_cursor.getString(2),
                        // get type
                        workoutRecords_weights_cursor.getString(6),
                        // get sets
                        workoutRecords_weights_cursor.getInt(3),
                        // get reps
                        workoutRecords_weights_cursor.getInt(4),
                        // get weight
                        workoutRecords_weights_cursor.getFloat(5)
                );

            }
            while (workoutRecords_weights_cursor.moveToNext());

            workoutRecords_weights_cursor.close();
        }

        db.close();

        return workoutRecord;
    }

    public Boolean DeleteWeightsWorkout(String id){
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        // returns format: workoutID | weightsID
        String weightsQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_WEIGHTS + "." + COLUMN_ID  +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_WEIGHTS + " ON " +
                TABLE_WEIGHTS + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WEIGHTSID +
                " WHERE " + TABLE_WORKOUT + "." + COLUMN_ID + " == " + id;


        // get any weights records
        Cursor workoutRecords_weights_cursor = db.rawQuery(weightsQuery, null);

        // delete workout id from workout table after all related e_weights and r_workout_exercise records
        int workoutID = 0;
        if(workoutRecords_weights_cursor.moveToFirst()){
            do {
                workoutID = workoutRecords_weights_cursor.getInt(0);
                int weightsID = workoutRecords_weights_cursor.getInt(1);
                db.delete(TABLE_WEIGHTS, COLUMN_ID + " = ?", new String[] {String.valueOf(weightsID)});
                db.delete(TABLE_WORKOUT_EXERCISE, COLUMN_WEIGHTSID + " = ?",
                        new String[] {String.valueOf(weightsID)});

            }
            while (workoutRecords_weights_cursor.moveToNext());

            workoutRecords_weights_cursor.close();
            result = true;
        }
        // after deleting all records for given workout in e_weights and r_workout_exercise
        // delete from workout table
        db.delete(TABLE_WORKOUT, COLUMN_ID + " = ?", new String[] {String.valueOf(workoutID)});

        workoutRecords_weights_cursor.close();
        db.close();


        return result;
    }


    public WorkoutRecord getCardioWorkout(String id){
        WorkoutRecord workoutRecord = new WorkoutRecord();

        SQLiteDatabase db = this.getReadableDatabase();
        // returns format: workoutID | date | Exercise name | Time | Distance | type
        String cardioQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_WORKOUT + "." + COLUMN_DATE + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_CARDIO + "." + COLUMN_TIME + ", " +
                TABLE_CARDIO + "." + COLUMN_DISTANCE + ", " +
                TABLE_TYPE + "." + COLUMN_NAME +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_EXERCISE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_EXERCISE_TYPE + " ON " +
                TABLE_EXERCISE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_EXERCISID +
                " JOIN " + TABLE_TYPE + " ON " +
                TABLE_TYPE + "." + COLUMN_ID + " = " + TABLE_EXERCISE_TYPE + "." + COLUMN_TYPEID +
                " JOIN " + TABLE_CARDIO + " ON " +
                TABLE_CARDIO + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_CARDIOID +
                " WHERE " + TABLE_WORKOUT + "." + COLUMN_ID + " == " + id;


        // get any weights records
        Cursor workoutRecords_cardio_cursor = db.rawQuery(cardioQuery, null);

        if(workoutRecords_cardio_cursor.moveToFirst()){
            do {
                workoutRecord.setmId(workoutRecords_cardio_cursor.getInt(0));
                workoutRecord.setmDate(workoutRecords_cardio_cursor.getString(1));
                workoutRecord.addCardioRecord(
                        // set id for cardio record to 0,
                        // as it doesnt necessarily matter when getting records from database
                        0,
                        // get name
                        workoutRecords_cardio_cursor.getString(2),
                        // get time
                        workoutRecords_cardio_cursor.getString(3),
                        // get distance
                        workoutRecords_cardio_cursor.getFloat(4)
                );

            }
            while (workoutRecords_cardio_cursor.moveToNext());

            workoutRecords_cardio_cursor.close();
        }

        db.close();

        return workoutRecord;
    }

    public Boolean DeleteCardioWorkout(String id){
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        // returns format: workoutID | weightsID
        String cardioQuery = "SELECT " +
                TABLE_WORKOUT + "." + COLUMN_ID + ", " +
                TABLE_CARDIO + "." + COLUMN_ID  +
                " FROM " + TABLE_WORKOUT +
                " JOIN " + TABLE_WORKOUT_EXERCISE + " ON " +
                TABLE_WORKOUT + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_WORKOUTID +
                " JOIN " + TABLE_CARDIO + " ON " +
                TABLE_CARDIO + "." + COLUMN_ID + " = " + TABLE_WORKOUT_EXERCISE + "." + COLUMN_CARDIOID +
                " WHERE " + TABLE_WORKOUT + "." + COLUMN_ID + " == " + id;


        // get any weights records
        Cursor workoutRecords_cardio_cursor = db.rawQuery(cardioQuery, null);

        // delete workout id from workout table after all related e_weights and r_workout_exercise records
        int workoutID = 0;
        if(workoutRecords_cardio_cursor.moveToFirst()){
            do {
                workoutID = workoutRecords_cardio_cursor.getInt(0);
                int cardioID = workoutRecords_cardio_cursor.getInt(1);
                db.delete(TABLE_CARDIO, COLUMN_ID + " = ?", new String[] {String.valueOf(cardioID)});
                db.delete(TABLE_WORKOUT_EXERCISE, COLUMN_WEIGHTSID + " = ?",
                        new String[] {String.valueOf(cardioID)});

            }
            while (workoutRecords_cardio_cursor.moveToNext());

            workoutRecords_cardio_cursor.close();
            result = true;
        }
        // after deleting all records for given workout in e_weights and r_workout_exercise
        // delete from workout table
        db.delete(TABLE_WORKOUT, COLUMN_ID + " = ?", new String[] {String.valueOf(workoutID)});

        workoutRecords_cardio_cursor.close();
        db.close();


        return result;
    }

    public ArrayList<WeightsDto> getHeaviestChestLifts(){
        SQLiteDatabase db = this.getReadableDatabase();
        String weightQuery = "SELECT " +
                TABLE_WEIGHTS + "." + COLUMN_ID + ", " +
                TABLE_EXERCISE + "." + COLUMN_NAME + ", " +
                TABLE_TYPE + "." + COLUMN_NAME + ", " +
                TABLE_WEIGHTS + "." + COLUMN_SETS + ", " +
                TABLE_WEIGHTS + "." + COLUMN_REPS  +", " +
                TABLE_WEIGHTS + "." + COLUMN_WEIGHT  + " FROM " + TABLE_WEIGHTS +","
                + TABLE_TYPE +","+ TABLE_EXERCISE + "," + TABLE_WORKOUT_EXERCISE + "," + TABLE_EXERCISE_TYPE +
                " WHERE " + TABLE_WEIGHTS +"."+ COLUMN_ID +"="+ TABLE_WORKOUT_EXERCISE +"."+ COLUMN_WEIGHTSID +
                " AND " + TABLE_WORKOUT_EXERCISE +"."+ COLUMN_EXERCISID +"=" + TABLE_EXERCISE_TYPE +"."+ COLUMN_EXERCISID +
                " AND " + TABLE_EXERCISE_TYPE +"."+ COLUMN_TYPEID+"=" + TABLE_TYPE +"."+ COLUMN_ID+
                " AND " + TABLE_WORKOUT_EXERCISE +"."+ COLUMN_EXERCISID +"=" + TABLE_EXERCISE +"."+ COLUMN_ID+
                " ORDER BY " + TABLE_WEIGHTS + "." + COLUMN_WEIGHT + " DESC LIMIT 3";
        Cursor weightRecords =db.rawQuery(weightQuery,null);
        ArrayList<WeightsDto> weightsRecordsArrayList = new ArrayList<>();
        if (weightRecords.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                weightsRecordsArrayList.add(new WeightsDto(
                        weightRecords.getInt(0),
                        weightRecords.getString(1),
                        weightRecords.getString(2),
                        weightRecords.getInt(3),
                        weightRecords.getInt(4),
                        weightRecords.getFloat(5)));
            } while (weightRecords.moveToNext());
            // moving our cursor to next.
        }
        weightRecords.close();
        return weightsRecordsArrayList;
    }


}

