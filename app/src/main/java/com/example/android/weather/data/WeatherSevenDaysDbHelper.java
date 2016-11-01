package com.example.android.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohamed nagy on 9/12/2016.
 */
public class WeatherSevenDaysDbHelper extends SQLiteOpenHelper {

    /*
     * DATABASE SQLITE COMMANDS
     */

    // database information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weatherSevenDays.db";

    // database table components
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String STRING_TYPE = "TEXT";
    private static final String BLOB_TYPE = "BLOB";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String AUTO_INCREMENT = "AUTOINCREMENT";
    private static final String COMA = ",";
    private static final String SPACE = " ";
    private static final String NOT_NULL = "NOT NULL";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE"  +
            SPACE + WeatherContract.WeatherSevenDayEntry.TABLE_NAME  + "(" +
            WeatherContract.WeatherSevenDayEntry._ID + SPACE + INTEGER_TYPE +
            SPACE + PRIMARY_KEY + SPACE + AUTO_INCREMENT + COMA +
            WeatherContract.WeatherSevenDayEntry.COLUMN_DATE + SPACE +
            STRING_TYPE + SPACE + NOT_NULL + COMA +
            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION + SPACE +
            STRING_TYPE + SPACE + NOT_NULL + COMA +
            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MAX_DEGREE + SPACE +
            INTEGER_TYPE + SPACE + NOT_NULL + COMA +
            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MIN_DEGREE + SPACE +
            INTEGER_TYPE + SPACE + NOT_NULL + COMA +
            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION_ICON + SPACE +
            BLOB_TYPE + ");";

    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + WeatherContract.WeatherSevenDayEntry.TABLE_NAME+ ";";



    public WeatherSevenDaysDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
