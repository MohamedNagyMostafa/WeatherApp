/*
 * Date            : 9/9/2016 Fre .
 * Programmer      : Mohamed Nagy Mostafa Mohamed .
 * Language        : JAVA / JSON / XML .
 * APP Details     : Show The weather conditions within 16 day for pattern city
 *                   minimum / maximum temperature degrees .
 * Minimum android : Kitkat version 4.4 .
 */

package com.example.android.weather;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.weather.data.WeatherSevenDaysDatabase;
import com.example.android.weather.data.WeatherSevenDaysDbHelper;

import java.util.ArrayList;

public class WeatherMainActivity extends AppCompatActivity implements
        android.app.LoaderManager.LoaderCallbacks<ArrayList<WeatherDay>>{

    // list of Weather_list.xml ...
    // list of 7 days
    private ListView listView;
    // adapter to recycle items to save memory
    private WeatherAdapter adapter;
    // progress refresh swap
    private SwipeRefreshLayout swipeRefreshLayout;
    // weather Datebase
    private WeatherSevenDaysDbHelper weatherSevenDaysDbHelper;
    // location button
    private ImageButton locationButton;
    private android.app.FragmentManager fragmentManager;

    private static final int LOADER_NETWORK_ID = 1;
    private static final int LOADER_DOWNLOAD_IMAGE_ID = 2;
    private static String weatherURL=
            "http://api.apixu.com/v1/forecast.json?key=ec303694c8e2476b954130716161009&q=Giza&days=7";
    private static boolean newURL = false;

    private ArrayList<WeatherDay> weatherDays;
    /** set listeners **/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(networkIsConnected()){
                // Launch background thread to get data from network
                // Loader is used to save memory from leak
                // from create another background thread

                swipeRefreshLayout.setRefreshing(true);
                LoaderManager loaderManager = getLoaderManager();

                if(!newURL)
                {
                    loaderManager.initLoader(LOADER_NETWORK_ID,null,WeatherMainActivity.this).forceLoad();}
                else{
                    loaderManager.restartLoader(LOADER_NETWORK_ID,null,WeatherMainActivity.this).forceLoad();
                }
            }
            else {
                Toast.makeText(
                        WeatherMainActivity.this,"Check Your Network Connection",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };


    private ListView.OnScrollListener onScrollListener =
            new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    // set location button visible after scroll
                    //locationButton.setVisibility(View.VISIBLE);

                }

                @Override
                public void onScroll(AbsListView absListView,
                                     int firstVisibleItem,
                                     int visibleItemCount,
                                     int totalItemCount) {
                    boolean checkList = false;
                    if(listView != null && listView.getCount() > 0){
                        // set location button Invisible during scroll
//                        if(listView.getFirstVisiblePosition() != -1)
//                            locationButton.setVisibility(View.GONE);
                        // check if the first item of the list is visible
                        boolean firstItemVisible =
                                listView.getFirstVisiblePosition() == 0;
                        // check the top of the first item is visible
                        boolean topOfFirstITem =
                                listView.getChildAt(0).getTop() == 0;
                        checkList = firstItemVisible && topOfFirstITem;
                    }else{
                        checkList = true;
                    }
                    swipeRefreshLayout.setEnabled(checkList);

                }
            };

    private View.OnClickListener locationListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationDialog locationDialog =
                            new LocationDialog();
                    locationDialog.show(fragmentManager,"Location");
                    Log.e("clicked","done");
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);

        listView =
                (ListView) findViewById(R.id.list_view_weather);

        locationButton =
                (ImageButton) findViewById(R.id.location_btn);

        fragmentManager = getFragmentManager();
        locationButton.setOnClickListener(locationListener);

        // set refresh swap
        swipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.activity_main_swipe_refresh_layout) ;

        /** set style **/
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary);;
        /** set Listener **/
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        /** set settings of listView **/
        listView.setOnScrollListener(onScrollListener);
        // initialize days list
        weatherDays = new ArrayList<>();
        // Database initialize
        weatherSevenDaysDbHelper = new WeatherSevenDaysDbHelper(this);


        // get data from database if exist
        weatherDays = WeatherSevenDaysDatabase.
                setDataBaseToList(weatherDays,weatherSevenDaysDbHelper);

        // set data to screen if database has data
        if(!weatherDays.isEmpty()){
            displayList();
        }
        // at first time not network and database
            // if network is connected
        if(networkIsConnected() && weatherDays.isEmpty()){
            // Launch background thread to get data from network
            // Loader is used to save memory from leak
            // from create another background thread
            swipeRefreshLayout.setRefreshing(true);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_NETWORK_ID,null,WeatherMainActivity.this).forceLoad();
        }
        else if(!networkIsConnected() && weatherDays.isEmpty()) {
            Toast.makeText(
                    this,"Check your network connection",Toast.LENGTH_LONG).show();
        }

    }

    // check network Connection
    private boolean networkIsConnected(){

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    // Create New Loader Object
    @Override
    public Loader<ArrayList<WeatherDay>> onCreateLoader(int i, Bundle bundle) {
        switch (i){

            case LOADER_NETWORK_ID:
                return new LoaderWeather(this,weatherURL);

            case LOADER_DOWNLOAD_IMAGE_ID:
                return new LoaderDownloadImage(this,weatherDays);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherDay>> loader,
                               ArrayList<WeatherDay> weatherDaysLoader) {

        if(loader.getId() == LOADER_NETWORK_ID){
            // Download Images
            Log.e("first ground network","done");
            weatherDays = weatherDaysLoader;
            LoaderManager loaderManager = getLoaderManager();

            if(!newURL){
                loaderManager.initLoader(LOADER_DOWNLOAD_IMAGE_ID,null,this);
            }
            else{
                loaderManager.restartLoader(LOADER_DOWNLOAD_IMAGE_ID,null,this);
                newURL = false;
            }
        }

        if(loader.getId() == LOADER_DOWNLOAD_IMAGE_ID) {
            // set data to database
            WeatherSevenDaysDatabase.insertDataBase(
                    weatherSevenDaysDbHelper,weatherDaysLoader);

            // get data from database
            weatherDays =
                    WeatherSevenDaysDatabase.setDataBaseToList(
                            weatherDaysLoader,weatherSevenDaysDbHelper);
            displayList();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherDay>> loader) {
        adapter.clear();
    }

    private void displayList(){

        if(!weatherDays.isEmpty()) {
            adapter = new WeatherAdapter
                    (this, weatherDays);
            listView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
        }
        else{
            Toast.makeText(this,"Error is happened\nPlease refresh again",Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    // set new url
    public static void createNewURL(String city){
        weatherURL =
                "http://api.apixu.com/v1/forecast.json?" +
                        "key=ec303694c8e2476b954130716161009&" +
                        "q="+ city +
                        "&days=7";
        newURL = true;
    }
}
