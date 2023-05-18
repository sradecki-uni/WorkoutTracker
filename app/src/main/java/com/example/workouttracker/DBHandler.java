package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import androidx.annotation.Nullable;
// import

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDB.db";
    private static final String TABLE_WORKOUTTYPE = "WorkoutType";
    private static final String TABLE_WEIGHTS = "Weights";
    private static final String TABLE_WEIGHTNAMES = "WeightNames";
    private static final String TABLE_CARDIO = "Cardio";
    private static final String TABLE_CARDIOWORKOUT = "CardioWorkout";
    private static final String TABLE_GLOBALTABLE = "GlobalTable";


    // used for name field in e_type, e_exercise tables
    // e_workout_type
    public static final String COLUMN_TYPE_ID = "type_id";
    public static final String COLUMN_TYPE = "type";

    // e_weights
    public static final String COLUMN_WEIGHT_ID = "weight_id";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";

    // e_weights_names
// The column name "weight_id" already defined above for e_weights table
    public static final String COLUMN_NAME = "name";

    // e_global_workout_table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_WORKOUT_TYPE = "workout_type";
    public static final String COLUMN_DATE = "date";

    // e_cardio
    public static final String COLUMN_CARDIO_ID = "cardio_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DISTANCE = "distance";

// e_cardio_names
// The column name "cardio_id" already defined above for e_cardio table
// The column name "name" already defined above for e_weights_names table

// The table "CardioWorkout" does not have any unique column names
// that have not been defined above in the column name constants.




    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTTYPE_TABLE = "CREATE TABLE " + TABLE_WORKOUTTYPE + " (" +
                "type_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT NOT NULL UNIQUE);";

        String CREATE_WEIGHTS_TABLE = "CREATE TABLE " + TABLE_WEIGHTS + " (" +
                "weight_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sets INT NOT NULL, " +
                "reps INT NOT NULL, " +
                "weight FLOAT NOT NULL, " +
                "type_id INT, " +
                "FOREIGN KEY(type_id) REFERENCES " + TABLE_WORKOUTTYPE + "(type_id));";

        String CREATE_WEIGHTNAMES_TABLE = "CREATE TABLE " + TABLE_WEIGHTNAMES + " (" +
                "weight_id INT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "FOREIGN KEY(weight_id) REFERENCES " + TABLE_WEIGHTS + "(weight_id));";

        String CREATE_CARDIO_TABLE = "CREATE TABLE " + TABLE_CARDIO + " (" +
                "cardio_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL);";

        String CREATE_CARDIOWORKOUT_TABLE = "CREATE TABLE " + TABLE_CARDIOWORKOUT + " (" +
                "cardio_id INT, " +
                "time INT NOT NULL, " +
                "distance FLOAT NOT NULL, " +
                "PRIMARY KEY (cardio_id, time, distance), " +
                "FOREIGN KEY (cardio_id) REFERENCES " + TABLE_CARDIO + "(cardio_id));";

        String CREATE_GLOBALTABLE_TABLE = "CREATE TABLE " + TABLE_GLOBALTABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date DATE NOT NULL, " +
                "exercise_id INT, " +
                "workout_type ENUM('cardio', 'weights') NOT NULL, " +
                "FOREIGN KEY(exercise_id) REFERENCES " + TABLE_WEIGHTS + "(weight_id), " +
                "FOREIGN KEY(exercise_id) REFERENCES " + TABLE_CARDIOWORKOUT + "(cardio_id));";

        db.execSQL(CREATE_WORKOUTTYPE_TABLE);
        db.execSQL(CREATE_WEIGHTS_TABLE);
        db.execSQL(CREATE_WEIGHTNAMES_TABLE);
        db.execSQL(CREATE_CARDIO_TABLE);
        db.execSQL(CREATE_CARDIOWORKOUT_TABLE);
        db.execSQL(CREATE_GLOBALTABLE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBALTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDIOWORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTNAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTTYPE);
        onCreate(db);

    }
//    public void createTypeTable(){
//        SQLiteDatabase db_write = this.getWritableDatabase();
//
//        // create predefined types for type tables
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, "Chest");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Arms");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Abs");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Shoulders");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Legs");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Cardio");
//        db_write.insert(TABLE_TYPE, null, values);
//        values.put(COLUMN_NAME, "Back");
//        db_write.insert(TABLE_TYPE, null, values);
//        db_write.close();
//    }
    public void addWorkoutType(String type){
        ContentValues values = new ContentValues();
        values.put("type", type);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_WORKOUTTYPE, null, values);
        db.close();
    }

    public void addWeightNames(int weight_id, String name){
        ContentValues values = new ContentValues();
        values.put("weight_id", weight_id);
        values.put("name", name);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_WEIGHTNAMES, null, values);
        db.close();
    }

    public void addCardio(String name){
        ContentValues values = new ContentValues();
        values.put("name", name);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CARDIO, null, values);
        db.close();
    }

    public void addCardioWorkout(int cardio_id, int time, float distance){
        ContentValues values = new ContentValues();
        values.put("cardio_id", cardio_id);
        values.put("time", time);
        values.put("distance", distance);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CARDIOWORKOUT, null, values);
        db.close();
    }

    public void addGlobalTableRecord(String date, int exercise_id, String workout_type){
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("exercise_id", exercise_id);
        values.put("workout_type", workout_type);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GLOBALTABLE, null, values);
        db.close();
    }


    public int getNewestWorkoutTypeID(){
        int id;
        try {
            String query = "SELECT MAX(" + COLUMN_TYPE_ID + ") FROM " + TABLE_WORKOUTTYPE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }

        return id;
    }

    public int getNewestWeightID(){
        int id;
        try {
            String query = "SELECT MAX(" + COLUMN_WEIGHT_ID + ") FROM " + TABLE_WEIGHTS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }

    public int getNewestCardioID(){
        int id;
        try {
            String query = "SELECT MAX(" + COLUMN_CARDIO_ID + ") FROM " + TABLE_CARDIO;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }

    public int getWeightID(WeightsNamesRecord wr){
        int id;
        try {
            String query = "SELECT " + COLUMN_WEIGHT_ID + " FROM " + TABLE_WEIGHTNAMES + " WHERE "
                    + COLUMN_NAME + " = '"  + wr.getName() + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }

    public int getCardioID(CardioRecord cr){
        int id;
        try {
            String query = "SELECT " + COLUMN_CARDIO_ID + " FROM " + TABLE_CARDIOWORKOUT + " WHERE "
                    + COLUMN_TIME + " = "  + cr.getTime() + " AND " + COLUMN_DISTANCE + " = " + cr.getDistance();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }

    public int getWorkoutTypeID(WorkoutTypeRecord wr){
        int id;
        try {
            String query = "SELECT " + COLUMN_TYPE_ID + " FROM " + TABLE_WORKOUTTYPE + " WHERE "
                    + COLUMN_TYPE + " = '"  + wr.getType() + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }

    public int getGlobalTableID(GlobalTableRecord gr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_GLOBALTABLE + " WHERE "
                    + COLUMN_EXERCISE_ID + " = "  + gr.getExerciseID() + " AND " + COLUMN_DATE + " = '" + gr.getDate() + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
            db.close();
        }
        catch (Exception e){
            id = 1;
        }
        return id;
    }


    public void addWorkoutType(int typeId, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_ID, typeId);
        values.put(COLUMN_NAME, name);

        try {
            db.insertOrThrow(TABLE_WORKOUTTYPE,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        db.close();
    }

    public void addWeights(int weightId, String name, int sets, int reps, int weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT_ID, weightId);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SETS, sets);
        values.put(COLUMN_REPS, reps);
        values.put(COLUMN_WEIGHT, weight);

        try {
            db.insertOrThrow(TABLE_WEIGHTS,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        db.close();
    }

    public void addCardio(int cardioId, int time, int distance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CARDIO_ID, cardioId);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DISTANCE, distance);

        try {
            db.insertOrThrow(TABLE_CARDIO,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        db.close();
    }

    public void addCardioWorkout(int workoutId, int cardioId, int typeId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_ID, workoutId);
        values.put(COLUMN_CARDIO_ID, cardioId);
        values.put(COLUMN_TYPE_ID, typeId);

        try {
            db.insertOrThrow(TABLE_CARDIOWORKOUT,null,values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        db.close();
    }

    public void addGlobalTable(int id, String date, int exerciseId, int typeId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_EXERCISE_ID, exerciseId);
        values.put(COLUMN_TYPE_ID, typeId);

        try {
            db.insertOrThrow(TABLE_GLOBALTABLE,null,values);
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

    public ArrayList<CardioRecord> getLongestCardioSessions(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cardioRecords = db.rawQuery("SELECT * FROM " + TABLE_CARDIO + " ORDER BY " + COLUMN_TIME + " DESC LIMIT 3", null);
        ArrayList<CardioRecord> cardioRecordsArrayList = new ArrayList<>();
        if (cardioRecords.moveToFirst()) {
            do {
                cardioRecordsArrayList.add(new CardioRecord(
                        cardioRecords.getInt(0),
                        "cardio",
                        cardioRecords.getString(1),
                        cardioRecords.getFloat(2)));
            } while (cardioRecords.moveToNext());
        }
        cardioRecords.close();
        return cardioRecordsArrayList;
    }


}
