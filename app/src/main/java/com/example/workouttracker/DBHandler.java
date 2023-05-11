package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

}
