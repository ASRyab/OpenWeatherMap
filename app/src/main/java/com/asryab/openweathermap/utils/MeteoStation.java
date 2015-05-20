package com.asryab.openweathermap.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.asryab.openweathermap.data.Coords;
import com.asryab.openweathermap.data.CurrentWeather;
import com.asryab.openweathermap.data.ForecastDay;
import com.asryab.openweathermap.data.ForecastWeather;
import com.asryab.openweathermap.data.MainParameters;
import com.asryab.openweathermap.data.StateParameters;
import com.asryab.openweathermap.data.WindParameters;
import com.asryab.openweathermap.des.CoordsDes;
import com.asryab.openweathermap.des.CurrentWeatherDes;
import com.asryab.openweathermap.des.ForecastDayDes;
import com.asryab.openweathermap.des.ForecastWeatherDes;
import com.asryab.openweathermap.des.MainParametersDes;
import com.asryab.openweathermap.des.StateParametersDes;
import com.asryab.openweathermap.des.WindParametersDes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

public class MeteoStation {
    private static final String AUTHORITY = "api.openweathermap.org";

    private static final Uri CURRENT = new Uri.Builder().scheme("http").authority(AUTHORITY)
            .appendPath("data").appendPath("2.5").appendPath("weather").build();
    public static final Uri ICON_LOAD = new Uri.Builder().scheme("http").authority(AUTHORITY)
            .appendPath("img").appendPath("w").build();
    private static final String LOG_TAG = MeteoStation.class.getName();

    private static MeteoStation sInstance;

    private final OkHttpClient mClient;
    private final Gson mGson;

    private MeteoStation() {
        mClient = new OkHttpClient();
        mGson = new GsonBuilder()
                .registerTypeAdapter(Coords.class, new CoordsDes())
                .registerTypeAdapter(MainParameters.class, new MainParametersDes())
                .registerTypeAdapter(StateParameters.class, new StateParametersDes())
                .registerTypeAdapter(WindParameters.class, new WindParametersDes())
                .registerTypeAdapter(CurrentWeather.class, new CurrentWeatherDes())
                .registerTypeAdapter(ForecastDay.class, new ForecastDayDes())
                .registerTypeAdapter(ForecastWeather.class, new ForecastWeatherDes())
                .create();
    }

    public static synchronized MeteoStation getInstance() {
        if (sInstance == null) {
            sInstance = new MeteoStation();
        }
        return sInstance;
    }


    public void getCurrentWeather(String city, String country, @NonNull final MeteoCallback<CurrentWeather> callback) {
        if (TextUtils.isEmpty(city) && TextUtils.isEmpty(country)) {
            throw new IllegalArgumentException();
        }
        String location = city;
        if (!TextUtils.isEmpty(country)) {
            location = new StringBuilder(location).append(",").append(country).toString();
        }

        String url = CURRENT.buildUpon().appendQueryParameter("q", location).build().toString();
        Log.d(LOG_TAG, "url=" + url);
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(LOG_TAG, "response=" + response.message());
                if (response.isSuccessful()) {
                    try {
                            CurrentWeather result = mGson.fromJson(response.body().charStream(), CurrentWeather.class);
                            callback.onSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, "error");
                        callback.onFail(null);
                    }
                }
            }
        });
    }


    public interface MeteoCallback<T> {
        void onSuccess(T result);

        void onFail(Exception e);
    }
}