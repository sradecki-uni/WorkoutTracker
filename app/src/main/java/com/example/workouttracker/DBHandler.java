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

    public static final String COLUMN_WORKOUT_ID = "workout_id";
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

    public int getWeightID(WeightsNameRecord wr){
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

    public int getGlobalTableID(GlobalWorkoutTableRecord gr){
        int id;
        try {
            String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_GLOBALTABLE + " WHERE "
                    + COLUMN_EXERCISE_ID + " = "  + gr.getExerciseTypeId() + " AND " + COLUMN_DATE + " = '" + gr.getDate() + "'";
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
    public WorkoutTypeRecord findWorkoutType(String type){
        String query = "SELECT * FROM " + TABLE_WORKOUTTYPE + " WHERE " + COLUMN_NAME + " = \"" + type + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        WorkoutTypeRecord record = new WorkoutTypeRecord();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            record.setTypeId(cursor.getInt(0));
            record.setName(cursor.getString(1));
        }
        else{
            record = null;
        }
        db.close();
        return record;
    }

    public WeightsRecord findWeightsExercise(String exercise){
        String query = "SELECT * FROM " + TABLE_WEIGHTS + " WHERE " + COLUMN_NAME + " = \"" + exercise + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        WeightsRecord wr = new WeightsRecord();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            wr.setWeightId(cursor.getInt(0));
            wr.setSets(cursor.getInt(1));
            wr.setReps(cursor.getInt(2));
            wr.setWeight(cursor.getFloat(3));
            wr.setTypeId(cursor.getInt(4));
        }
        else{
            wr = null;
        }
        db.close();
        return wr;
    }

    public CardioRecord findCardioExercise(String exercise){
        String query = "SELECT * FROM " + TABLE_CARDIO + " WHERE " + COLUMN_NAME + " = \"" + exercise + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CardioRecord cr = new CardioRecord();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            cr.setCardioId(cursor.getInt(0));
            cr.setTime(cursor.getInt(1));
            cr.setDistance(cursor.getInt(2));
        }
        else{
            cr = null;
        }
        db.close();
        return cr;
    }

    public GlobalWorkoutTableRecord findGlobalTable(int id){
        String query = "SELECT * FROM " + TABLE_GLOBALTABLE + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GlobalWorkoutTableRecord record = new GlobalWorkoutTableRecord();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            record.setId(cursor.getInt(0));
            // assuming that column index corresponds to the order in class
            record.setWorkoutTypeId(cursor.getInt(1));
            record.setWeightsId(cursor.getInt(2));
            record.setWeightNameId(cursor.getInt(3));
            record.setCardioId(cursor.getInt(4));
            record.setCardioWorkoutId(cursor.getInt(5));
            record.setDate(new Date(cursor.getLong(6))); // assuming date is stored as long in SQLite
            record.setGlobalTableId(cursor.getInt(7));
            record.setExerciseTypeId(cursor.getInt(8));
        }
        else{
            record = null;
        }
        db.close();
        return record;
    }



    // might need to adjust this to query the r_workout_exercise table
    public boolean deleteWorkoutTypeRecord(String typeName){
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_WORKOUTTYPE + " WHERE " + COLUMN_NAME + " = \"" + typeName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_WORKOUTTYPE, COLUMN_ID + " = ?",
                    new String [] {String.valueOf(id)});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteWeightsRecord(String exerciseName){
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_WEIGHTS + " WHERE " + COLUMN_NAME + " = \"" + exerciseName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_WEIGHTS, COLUMN_ID + " = ?",
                    new String [] {String.valueOf(id)});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteCardioRecord(String exerciseName){
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_CARDIO + " WHERE " + COLUMN_NAME + " = \"" + exerciseName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_CARDIO, COLUMN_ID + " = ?",
                    new String [] {String.valueOf(id)});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteGlobalTableRecord(int id){
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        int deletionCount = db.delete(TABLE_GLOBALTABLE, COLUMN_ID + " = ?",
                new String [] {String.valueOf(id)});

        if(deletionCount > 0){
            result = true;
        }

        db.close();
        return result;
    }


    public ArrayList<WorkoutTypeRecord> getLongestWorkoutTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor workoutTypesRecords = db.rawQuery("SELECT * FROM " + TABLE_WORKOUTTYPE + " ORDER BY " + COLUMN_TIME + " DESC LIMIT 3", null);
        ArrayList<WorkoutTypeRecord> workoutTypeRecordsArrayList = new ArrayList<>();
        if (workoutTypesRecords.moveToFirst()) {
            do {
                workoutTypeRecordsArrayList.add(new WorkoutTypeRecord(
                        workoutTypesRecords.getInt(0),
                        workoutTypesRecords.getString(1),
                        workoutTypesRecords.getFloat(2)));
            } while (workoutTypesRecords.moveToNext());
        }
        workoutTypesRecords.close();
        return workoutTypeRecordsArrayList;
    }

    public ArrayList<WeightsRecord> getHeaviestWeightsSessions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor weightsRecords = db.rawQuery("SELECT * FROM " + TABLE_WEIGHTS + " ORDER BY " + COLUMN_WEIGHT + " DESC LIMIT 3", null);
        ArrayList<WeightsRecord> weightsRecordsArrayList = new ArrayList<>();
        if (weightsRecords.moveToFirst()) {
            do {
                weightsRecordsArrayList.add(new WeightsRecord(
                        weightsRecords.getInt(0),
                        weightsRecords.getString(1),
                        weightsRecords.getFloat(2)));
            } while (weightsRecords.moveToNext());
        }
        weightsRecords.close();
        return weightsRecordsArrayList;
    }

    public ArrayList<CardioRecord> getLongestCardioSessions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cardioRecords = db.rawQuery("SELECT * FROM " + TABLE_CARDIO + " ORDER BY " + COLUMN_TIME + " DESC LIMIT 3", null);
        ArrayList<CardioRecord> cardioRecordsArrayList = new ArrayList<>();
        if (cardioRecords.moveToFirst()) {
            do {
                cardioRecordsArrayList.add(new CardioRecord(
                        cardioRecords.getInt(0),
                        cardioRecords.getString(1),
                        cardioRecords.getFloat(2)));
            } while (cardioRecords.moveToNext());
        }
        cardioRecords.close();
        return cardioRecordsArrayList;
    }



}
