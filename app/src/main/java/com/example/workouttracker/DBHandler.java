package com.example.workouttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDB.db";

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

    }


}
