package com.asryab.openweathermap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;

import com.asryab.openweathermap.utils.MeteoStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.asryab.openweathermap.data.CurrentWeather;
import com.asryab.openweathermap.data.ForecastWeather;

public class MeteoService extends Service
{
    public static final String TAG = MeteoService.class.getName();

    private Context mContext;
    private Gson mGson;

    public abstract class Action
    {
        public static final String CURRENT_WEATHER_BY_COORDS = "com.asryab.weather.action_currentweatherbycoords";
        public static final String CURRENT_WEATHER_BY_CITY = "com.asryab.weather.action_currentweatherbycity";
        public static final String FORECAST_WEATHER_BY_COORDS = "com.asryab.weather.action_forecastbycoords";
        public static final String FORECAST_WEATHER_BY_CITY = "com.asryab.weather.action_forecastbycity";
    }

    public abstract class BundleKey
    {
        public static final  String LON="lon";
        public static final  String LAT = "lat";
        public static final  String CITY = "city";
        public static final  String COUNTRY = "country";
        public static final  String DAYS_COUNT = "days_count";
        public static final  String PREFERENCE = "preference";
        public static final  String CALL_BACK = "call back";
        public static final  String CURRENT_WEATHER = "current weather";
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = getApplication().getApplicationContext();
        mGson = new GsonBuilder().create();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (Action.CURRENT_WEATHER_BY_CITY.equals(intent.getAction()))
        {
            final String city = intent.getStringExtra(BundleKey.CITY);
//            final String country = intent.getStringExtra(BundleKey.COUNTRY);
//            final String prefKey = intent.getStringExtra(BundleKey.PREFERENCE);
            final ResultReceiver callback = intent.getParcelableExtra(BundleKey.CALL_BACK);

            MeteoStation.getInstance().getCurrentWeather(city, null, new MeteoStation.MeteoCallback<CurrentWeather>()
            {
                @Override
                public void onSuccess(CurrentWeather result)
                {
                    Bundle bundle= new Bundle();
                    bundle.putParcelable(BundleKey.CURRENT_WEATHER,result);
                    callback.send(MapsActivity.RESULT_CODE_OK, bundle);
                    String jsonString = mGson.toJson(result, CurrentWeather.class);
                    Logger.json(jsonString);
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, null).apply();
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, jsonString).apply();
                }

                @Override
                public void onFail(Exception e)
                {
                    Logger.e(e, String.format( "Current Weather: city - %1$s, country - %2$s", city, null));
                    callback.send(MapsActivity.RESULT_CODE_ERROR, new Bundle());
                }
            });

        }
//        else if (Action.CURRENT_WEATHER_BY_COORDS.equals(intent.getAction()))
//        {
//            final double lat = intent.getDoubleExtra(BundleKey.LAT, 0);
//            final double lon = intent.getDoubleExtra(BundleKey.LON, 0);
//            final String prefKey = intent.getStringExtra(BundleKey.PREFERENCE);
//
//            MeteoStation.getInstance().getCurrentWeather(lat, lon, new MeteoStation.MeteoCallback<CurrentWeather>()
//            {
//                @Override
//                public void onSuccess(CurrentWeather result)
//                {
//                    String jsonString = mGson.toJson(result, CurrentWeather.class);
//                    Logger.json(jsonString);
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, null).apply();
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, jsonString).apply();
//                }
//
//                @Override
//                public void onFail(Exception e)
//                {
//                    Logger.e(e, String.format("Current Weather: lat - %1$f , lon - %2$f", lat, lon));
//                }
//            });
//        }
//        else if (Action.FORECAST_WEATHER_BY_CITY.equals(intent.getAction()))
//        {
//            final String city = intent.getStringExtra(BundleKey.CITY);
//            final String country = intent.getStringExtra(BundleKey.COUNTRY);
//            final int days = intent.getIntExtra(BundleKey.DAYS_COUNT, 7);
//            final String prefKey = intent.getStringExtra(BundleKey.PREFERENCE);
//
//            MeteoStation.getInstance().getForecastWeather(city, country, days, new MeteoStation.MeteoCallback<ForecastWeather>()
//            {
//                @Override
//                public void onSuccess(ForecastWeather result)
//                {
//                    String jsonString = mGson.toJson(result, ForecastWeather.class);
//                    Logger.json(jsonString);
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, null).apply();
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, jsonString).apply();
//                }
//
//                @Override
//                public void onFail(Exception e)
//                {
//                    Logger.e(e, String.format("Forecast Weather: city - %1$s, country - %2$s, days - %3$d", city, country, days));
//                }
//            });
//        }
//        else if (Action.FORECAST_WEATHER_BY_COORDS.equals(intent.getAction()))
//        {
//            final double lat = intent.getDoubleExtra(BundleKey.LAT, 0);
//            final double lon = intent.getDoubleExtra(BundleKey.LON, 0);
//            final int days = intent.getIntExtra(BundleKey.DAYS_COUNT, 7);
//            final String prefKey = intent.getStringExtra(BundleKey.PREFERENCE);
//
//            MeteoStation.getInstance().getForecastWeather(lat, lon, days, new MeteoStation.MeteoCallback<ForecastWeather>()
//            {
//                @Override
//                public void onSuccess(ForecastWeather result)
//                {
//                    String jsonString = mGson.toJson(result, ForecastWeather.class);
//                    Logger.json(jsonString);
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, null).apply();
//                    PreferenceManager.getDefaultSharedPreferences(mContext)
//                            .edit().putString(prefKey, jsonString).apply();
//                }
//
//                @Override
//                public void onFail(Exception e)
//                {
//                    Logger.e(e, String.format("Forecast Weather: lat - %1$f, lon - %2$f, days - %3$d", lat, lon, days));
//                }
//            });
//        }
        return super.onStartCommand(intent, flags, startId);
    }
}
