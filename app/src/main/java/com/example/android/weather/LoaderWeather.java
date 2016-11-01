package com.example.android.weather;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mohamed nagy on 9/11/2016.
 * get URL of page
 * make request from network and convert page to JSON
 * fill WeatherDay objects their data
 * by WeatherNetwork class
 * return list to set into adapter
 */
public class LoaderWeather extends AsyncTaskLoader<ArrayList<WeatherDay>> {

    private static String pageUrl;

    public LoaderWeather(Context context,String Url){
        super(context);
        pageUrl = Url;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<WeatherDay> loadInBackground() {

        String pageJSON = WeatherNetWork.httpRequest(pageUrl);
        ArrayList<WeatherDay> weatherDays =
                WeatherNetWork.generateDaysList(pageJSON);
        return weatherDays;
    }
}
