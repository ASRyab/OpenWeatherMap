package com.asryab.openweathermap.utils;

import android.content.Context;
import android.content.Intent;

import com.asryab.openweathermap.MeteoService;


public class MeteoServiceManager
{
    private MeteoServiceManager(){}

    public static void getCurrentWeather(Context context, String city,
                                          String country, String preferenceKey)
    {
        Intent intent = new Intent(context, MeteoService.class)
            .setAction(MeteoService.Action.CURRENT_WEATHER_BY_CITY)
            .putExtra(MeteoService.BundleKey.CITY, city)
            .putExtra(MeteoService.BundleKey.COUNTRY, country)
            .putExtra(MeteoService.BundleKey.PREFERENCE, preferenceKey);
        context.startService(intent);
    }

    public static void getCurrentWeather(Context context, double lat,
                                          double lon, String preferenceKey)
    {
        Intent intent = new Intent(context, MeteoService.class)
                .setAction(MeteoService.Action.CURRENT_WEATHER_BY_COORDS)
                .putExtra(MeteoService.BundleKey.LAT, lat)
                .putExtra(MeteoService.BundleKey.LON, lon)
                .putExtra(MeteoService.BundleKey.PREFERENCE, preferenceKey);
        context.startService(intent);
    }

    public static void getForecastWeather(Context context,
                                           String city, String country, int days,
                                           String preferenceKey)
    {
        Intent intent = new Intent(context, MeteoService.class)
                .setAction(MeteoService.Action.FORECAST_WEATHER_BY_CITY)
                .putExtra(MeteoService.BundleKey.CITY, city)
                .putExtra(MeteoService.BundleKey.COUNTRY, country)
                .putExtra(MeteoService.BundleKey.DAYS_COUNT, days)
                .putExtra(MeteoService.BundleKey.PREFERENCE, preferenceKey);
        context.startService(intent);
    }

    public  static void  getForecastWeather(Context context,
                                            double lat, double lon, int days,
                                            String preferenceKey)
    {
        Intent intent = new Intent(context, MeteoService.class)
                .setAction(MeteoService.Action.FORECAST_WEATHER_BY_COORDS)
                .putExtra(MeteoService.BundleKey.LAT, lat)
                .putExtra(MeteoService.BundleKey.LON, lon)
                .putExtra(MeteoService.BundleKey.DAYS_COUNT, days)
                .putExtra(MeteoService.BundleKey.PREFERENCE, preferenceKey);
        context.startService(intent);
    }

    public static void getForecastWeather(Context context,
                                          String city, String country,
                                          String preferenceKey)
    {
        getForecastWeather(context, city, country, 7, preferenceKey);
    }

    public  static void  getForecastWeather(Context context,
                                            double lat, double lon,
                                            String preferenceKey)
    {
        getForecastWeather(context, lat, lon, 7, preferenceKey);
    }
}
