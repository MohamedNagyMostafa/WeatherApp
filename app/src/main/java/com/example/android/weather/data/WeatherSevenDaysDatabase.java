package com.example.android.weather.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.weather.WeatherDay;

import java.util.ArrayList;

/**
 * Created by mohamed nagy on 9/12/2016.
 */
public class WeatherSevenDaysDatabase {

    public WeatherSevenDaysDatabase()
    {}

    public static void insertDataBase(
            WeatherSevenDaysDbHelper weatherSevenDaysDbHelper,
            ArrayList<WeatherDay> weatherDays){
        SQLiteDatabase sqLiteDatabase =
                weatherSevenDaysDbHelper.getReadableDatabase();
        // Remove previous data
        sqLiteDatabase.delete(
                WeatherContract.WeatherSevenDayEntry.TABLE_NAME,
                null,
                null);

        try {
            if (weatherDays.size() > 0) {
                Log.e("database","created");
                for (int i = 0; i < weatherDays.size(); i++) {

                    ContentValues contentValues =
                            new ContentValues();
                    contentValues.put(
                            WeatherContract.WeatherSevenDayEntry.COLUMN_DATE,
                            weatherDays.get(i).getStringDate());

                    contentValues.put(
                            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION,
                            weatherDays.get(i).getWeatherCondition());

                    contentValues.put(
                            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MAX_DEGREE,
                            weatherDays.get(i).getMaxDegree());

                    contentValues.put(
                            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MIN_DEGREE,
                            weatherDays.get(i).getMinDegree());

                    contentValues.put(
                            WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION_ICON,
                            weatherDays.get(i).getImageAsBytesArray());

                    sqLiteDatabase.insert(WeatherContract.WeatherSevenDayEntry.TABLE_NAME,
                            null, contentValues);
                }
            }
        }catch (NullPointerException e){
            Log.e("error insert",e.toString());
        }
        finally {
            sqLiteDatabase.close();
        }
    }


    public static ArrayList<WeatherDay> setDataBaseToList(
            ArrayList<WeatherDay>weatherDays,
             WeatherSevenDaysDbHelper weatherSevenDaysDbHelper){

            String date;
            String weatherCondition;
            byte[] imageCondition;
            int maxDegree;
            int minDegree;
            Log.e("database to list","done");
            SQLiteDatabase sqLiteDatabase =
                    weatherSevenDaysDbHelper.getReadableDatabase();

            String[] prodution ={
                    WeatherContract.WeatherSevenDayEntry._ID,
                    WeatherContract.WeatherSevenDayEntry.COLUMN_DATE,
                    WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION,
                    WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MAX_DEGREE,
                    WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MIN_DEGREE,
                    WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION_ICON
            };

            Cursor cursor = sqLiteDatabase.query(
                    WeatherContract.WeatherSevenDayEntry.TABLE_NAME,
                    prodution,
                    null,
                    null,
                    null,
                    null,
                    null
            );

        if(!weatherDays.isEmpty())
            weatherDays.clear();
            // get item from database

            while(cursor.moveToNext()){

                int currentIndex =
                        cursor.getColumnIndex(WeatherContract.WeatherSevenDayEntry.COLUMN_DATE);
                date = cursor.getString(currentIndex);

                currentIndex =
                        cursor.getColumnIndex(WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION);
                weatherCondition = cursor.getString(currentIndex);

                currentIndex =
                        cursor.getColumnIndex(WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MAX_DEGREE);
                maxDegree = cursor.getInt(currentIndex);

                currentIndex =
                        cursor.getColumnIndex(WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_MIN_DEGREE);
                minDegree = cursor.getInt(currentIndex);
                currentIndex =
                        cursor.getColumnIndex(WeatherContract.WeatherSevenDayEntry.COLUMN_WEATHER_DAY_CONDITION_ICON);
                imageCondition = cursor.getBlob(currentIndex);
                //set items within list

                boolean done = weatherDays.add(new WeatherDay(date,weatherCondition
                ,maxDegree,minDegree,imageCondition));
            }
            sqLiteDatabase.close();
            return weatherDays;
    }

}
