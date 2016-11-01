package com.example.android.weather.data;

import android.provider.BaseColumns;

/**
 * Created by mohamed nagy on 9/12/2016.
 */
public class WeatherContract {

    public WeatherContract(){}

    // Weather tables
    final static class WeatherSevenDayEntry implements BaseColumns{

        //Table NAme
        public final static String TABLE_NAME = "weather_seven_days";
        public final static String _ID = "_id";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_WEATHER_DAY_CONDITION = "condition";
        public final static String COLUMN_WEATHER_DAY_MAX_DEGREE = "maxDegree";
        public final static String COLUMN_WEATHER_DAY_MIN_DEGREE = "minDegree";
        public final static String COLUMN_WEATHER_DAY_CONDITION_ICON = "icon";
    }
}
