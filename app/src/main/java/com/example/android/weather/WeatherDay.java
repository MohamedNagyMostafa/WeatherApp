package com.example.android.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mohamed nagy on 9/10/2016.
 * Day object to set it's data
 */
public class WeatherDay {

    private String urlImage ;
    private String weatherCondition;
    private float maxDegree;
    private float minDegree ;
    private String stringDate;
    private Bitmap bitmapImage ;

    // Number of WeatherDay image which download
    // FROM API
    public WeatherDay(String urlImage,String weatherCondition,
                      float maxDegree,float minDegree,String stringDate){
        this.urlImage = urlImage;
        this.weatherCondition = weatherCondition;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
        this.stringDate = stringDate;
    }
    // FROM DATABASE
    public WeatherDay(String stringDate, String weatherCondition,
                      float maxDegree,float minDegree,byte[] imageCondition){
        this.stringDate = stringDate;
        this.weatherCondition = weatherCondition;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
        setBitmapImage(imageCondition);
    }

    public String getDayName() {
        Date date = convertStringToDate(stringDate);
        return new SimpleDateFormat("EEEE").format(date);
    }

    public String getStringDate(){
        return stringDate;
    }

    public String getURLImage() {
        return setProtocal();
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public float getMaxDegree() {
        return maxDegree;
    }

    public float getMinDegree() {
        return minDegree;
    }

    public Bitmap getBitmapImage(){return bitmapImage;}

    public byte[] getImageAsBytesArray(){
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG,100,
                byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        return bytes;
    }

    private Date convertStringToDate(String stringDate){
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd");
            date = new Date();
            date = simpleDateFormat.parse(stringDate);
        }catch (ParseException e){
            Log.e("Errror",e.toString());
        }
        return date;
    }

    private String setProtocal(){
        StringBuilder stringBuilder =
                new StringBuilder();
        stringBuilder.append("http:");
        stringBuilder.append(urlImage);
        return stringBuilder.toString();
    }

    public void setBitmapImage(Bitmap bitmap){
        bitmapImage = bitmap;
    }

    public void setBitmapImage(byte[] bytes){
        bitmapImage = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
