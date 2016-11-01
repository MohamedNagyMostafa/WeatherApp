package com.example.android.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohamed nagy on 9/10/2016.
 * get ArrayList of WeatherDay objects
 * get Views from activity_weather.xml which recycle
 * get data from WeatherDay objects and set it into their views
 * launch another background thread to download image icon for
 * each day in the list
 * adapter will be valid to using
 */
public class WeatherAdapter extends ArrayAdapter<WeatherDay>{

    private static final int FIFTY_DEGREE  = 50;
    private static final int FORTY_DEGREE  = 40;
    private static final int THIRTY_DEGREE = 30;
    private static final int TWENTY_DEGREE = 20;
    private static final int TEN_DEGREE    = 10;

    public WeatherAdapter(Context context, ArrayList<WeatherDay> list){
        super(context,0,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        // get weatherDay object
        WeatherDay day = getItem(position);
        // set recycle view (one item from list)
        View recycleItem = convertView;

        if(recycleItem == null){
            // set current item
            recycleItem = LayoutInflater
                    .from(getContext()).inflate(R.layout.activity_weather_main,parent,false);
        }

        // modify components of item
        TextView dayName =
                (TextView) recycleItem.findViewById(R.id.day_text);
        TextView weatherCondition =
                (TextView) recycleItem.findViewById(R.id.condition_text);
        TextView maxDegree =
                (TextView) recycleItem.findViewById(R.id.max_degree);
        TextView minDegree =
                (TextView) recycleItem.findViewById(R.id.min_degree);
        ImageView imageCondition =
                (ImageView) recycleItem.findViewById(R.id.condition_image);

        // set data to it components
        dayName.setText(day.getDayName());

        weatherCondition.setText(day.getWeatherCondition());
        maxDegree.setText(String.valueOf(day.getMaxDegree()) + " °C");
        minDegree.setText(String.valueOf(day.getMinDegree()) + " °C");

        // set image
        imageCondition.setImageBitmap(day.getBitmapImage());

        // set color for components
        int colorMax = getColor(day.getMaxDegree());
        int colorMin = getColor(day.getMinDegree());

        weatherCondition.setTextColor(
                getContext().getResources().getColor(colorMax));
        maxDegree.setTextColor(
                getContext().getResources().getColor(colorMax));
        minDegree.setTextColor(
                getContext().getResources().getColor(colorMin
        ));

        return recycleItem;
    }

    private int getColor(float degree){

        if(degree > FIFTY_DEGREE)
            return R.color.fifty_degree;
        else if(degree < FIFTY_DEGREE && degree >= FORTY_DEGREE)
            return R.color.fourty_degree;
        else if(degree < FORTY_DEGREE  && degree >= THIRTY_DEGREE)
            return R.color.thirty_degree;
        else if(degree < THIRTY_DEGREE && degree >= TWENTY_DEGREE)
            return R.color.twenty_degree;
        else if(degree < TWENTY_DEGREE && degree >= TEN_DEGREE)
            return R.color.ten_degree;
        else
            return R.color.zero_degree;
    }

}
