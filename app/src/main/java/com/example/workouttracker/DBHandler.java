package com.example.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// import

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDB.db";
    private static final String TABLE_WORKOUT = "weightWorkouts";

    public static final String COLUMN_WEIGHTS_EXERCISE = "weightsExercise";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_ID = "id";
    private static final String TYPE_TABLE = "";


    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_E_TYPE_TABLE = "CREATE TABLE e_type (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "name TEXT NOT NULL UNIQUE);";

        String CREATE_E_EXERCISE_TABLE = "CREATE TABLE e_exercise (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "name TEXT NOT NULL UNIQUE);";

        String CREATE_E_WORKOUT_TABLE = "CREATE TABLE e_workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "date TEXT NOT NULL);";

        String CREATE_E_WEIGHTS_TABLE = "CREATE TABLE e_weights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "sets UNSIGNED INTEGER DEFAULT 1, " +
                "reps UNSIGNED INTEGER DEFAULT 1, " +
                "weight UNSIGNED FLOAT DEFAULT 0.0 );";

        String CREATE_E_CARDIO_TABLE = "CREATE TABLE e_cardio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "time string DEFAULT '00:00:00', " +
                "distance UNSIGNED FLOAT DEFAULT 0.0 );";

        String CREATE_R_EXERCISE_TYPE_TABLE = "CREATE TABLE r_exercise_type (" +
                "exerciseID INTEGER, " +
                "typeID INTEGER);";

        String CREATE_R_WORKOUT_EXERCISE_TABLE = "CREATE TABLE r_workout_exercise (" +
                "workoutID INTEGER, " +
                "exerciseID INTEGER, " +
                "cardioID INTEGER, " +
                "weightsID INTEGER);";

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
    db.execSQL("DROP TABLE IF EXISTS " + TYPE_TABLE);
    onCreate(db);
    }

    public void addWeightsExercise( WeightsRecord wr){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, wr.getType());
        values.put(COLUMN_REPS, wr.getReps());
        values.put(COLUMN_WEIGHT, wr.getWeight());
        values.put(COLUMN_SETS, wr.getSets());
        values.put(COLUMN_WEIGHTS_EXERCISE, wr.getExercise());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_WORKOUT, null, values);
        db.close();
    }

    public WeightsRecord findWeightsExercise(String exercise){
        String query = "SELECT * FROM" + TABLE_WORKOUT + "WHERE " + COLUMN_WEIGHTS_EXERCISE + " = \"" + exercise + "\"";

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

    public boolean deleteWeightsRecord(String mWeight){
        boolean result = false;

        String query = "Select * From " + TABLE_WORKOUT + "WHERE " + COLUMN_WEIGHTS_EXERCISE + " = \"" + mWeight + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        WeightsRecord wr = new WeightsRecord();

        if(cursor.moveToFirst()){
            wr.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_WORKOUT,  COLUMN_WEIGHTS_EXERCISE + " = ?",
                    new String [] {String.valueOf(wr.getId())});
            cursor.close();
            result = true;

        }
        db.close();
        return result;

    }

}
