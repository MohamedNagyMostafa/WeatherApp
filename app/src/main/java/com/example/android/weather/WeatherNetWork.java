package com.example.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mohamed nagy on 9/10/2016.
 * get data from URL then Convert it into JSON
 * get data from JSON then Set it within ArrayList
 * from WeatherDay objects then return it
 */
public class WeatherNetWork {

    public WeatherNetWork()
    {}
    // get data from urlStr ..
    public static String httpRequest(String urlStr){

        URL url = createURL(urlStr);

        HttpURLConnection httpURLConnection = null;
        String pageJSON = null;
        try{
            Log.e("http request","done");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                pageJSON = getPageJSON(httpURLConnection.getInputStream());
            }
        }catch (IOException e){
            Log.e("Error",e.toString());
        }finally {
            httpURLConnection.disconnect();
        }

        return pageJSON;
    }

    // Convert inputStream from url page to JSON page
    private static String getPageJSON(InputStream stream) throws IOException{

        StringBuilder stringBuilder = new StringBuilder();
        if(stream != null){
            try {
                Log.e("JSON file","done");
                InputStreamReader inputStreamReader =
                        new InputStreamReader(stream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader =
                        new BufferedReader(inputStreamReader);

                String line;
                line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            }catch (NullPointerException e){
                Log.e("Error",e.toString());
            }
        }
        return stringBuilder.toString();
    }

    // Convert url String to URL object
    // check if String valid
    public static URL createURL(String urlStr){
        URL url = null;
        try{
            url = new URL(urlStr);
            Log.e("url is",urlStr);
        }catch (MalformedURLException e){
            Log.e("Error",e.toString());
        }
        return url;
    }

    // set data to weatherDay objects
    public static ArrayList<WeatherDay> generateDaysList(String pageJSON){

            ArrayList<WeatherDay> weatherDays = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(pageJSON);
                JSONObject forecase = jsonObject.getJSONObject("forecast");
                JSONArray forecastday = forecase.getJSONArray("forecastday");

                for(int i = 0 ; i < forecastday.length() ; i++){
                    JSONObject object = forecastday.getJSONObject(i);
                    JSONObject day = object.getJSONObject("day");
                    JSONObject condition=  day.getJSONObject("condition");
                    String date = object.getString("date");
                    String dayCondition = condition.getString("text");
                    String urlImage = condition.getString("icon");
                    float maxDegree = (float) day.getDouble("maxtemp_c");
                    float minDegree = (float) day.getDouble("mintemp_c");

                    weatherDays.add(
                            new WeatherDay(urlImage,dayCondition,maxDegree,minDegree,date));
                }
            }catch (JSONException e){
                Log.e("Error",e.toString());
            }
            catch (NullPointerException e){
                Log.e("Errror",e.toString());
            }

            return weatherDays;
    }
}
