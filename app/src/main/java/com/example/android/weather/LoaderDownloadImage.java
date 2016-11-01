package com.example.android.weather;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mohamed nagy on 9/11/2016.
 * get String url of image
 * convert it into bytes input
 * decode it into Bitmap
 * and return it to set in ImageView
 */
public class LoaderDownloadImage
        extends AsyncTaskLoader< ArrayList<WeatherDay>>{

    private ArrayList<WeatherDay> weatherDays;

    private static final int NUMBER_OF_DAYS = 7;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public LoaderDownloadImage(Context context,
                               ArrayList<WeatherDay> weatherDays){
        super(context);
        this.weatherDays = weatherDays;
    }

    @Override
    public ArrayList<WeatherDay> loadInBackground() {

        for(int i = 0 ;i < NUMBER_OF_DAYS ; i++){

            Bitmap bitmap = null;

            try {
                InputStream inputStream =
                        WeatherNetWork.createURL(
                                weatherDays.get(i).getURLImage()).openStream();

                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                Log.e("Error", e.toString());
            }
            finally {
                weatherDays.get(i).setBitmapImage(bitmap);
            }
        }
        return weatherDays;
    }
}
