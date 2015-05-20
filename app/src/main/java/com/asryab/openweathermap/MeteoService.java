package com.asryab.openweathermap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import com.asryab.openweathermap.data.CurrentWeather;
import com.asryab.openweathermap.utils.MeteoStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

public class MeteoService extends Service
{
    public static final String TAG = MeteoService.class.getName();

    private Context mContext;
    private Gson mGson;

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
        if (Action.CURRENT_WEATHER_BY_CITY.equals(intent.getAction())) {
            final String city = intent.getStringExtra(BundleKey.CITY);
            final ResultReceiver callback = intent.getParcelableExtra(BundleKey.CALL_BACK);

            MeteoStation.getInstance().getCurrentWeather(city, null, new MeteoStation.MeteoCallback<CurrentWeather>() {
                @Override
                public void onSuccess(CurrentWeather result) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BundleKey.CURRENT_WEATHER, result);
                    callback.send(MapsActivity.RESULT_CODE_OK, bundle);
                    String jsonString = mGson.toJson(result, CurrentWeather.class);
                    Logger.json(jsonString);
                }

                @Override
                public void onFail(Exception e) {
                    Logger.e(e, String.format("Current Weather: city - %1$s, country - %2$s", city, null));
                    callback.send(MapsActivity.RESULT_CODE_ERROR, new Bundle());
                }
            });

        }
//
        return super.onStartCommand(intent, flags, startId);
    }

    public abstract class Action
    {
        public static final String CURRENT_WEATHER_BY_CITY = "com.asryab.weather.action_currentweatherbycity";
    }

    public abstract class BundleKey
    {
        public static final String CITY = "city";
        public static final String CALL_BACK = "call back";
        public static final String CURRENT_WEATHER = "current weather";
    }
}
