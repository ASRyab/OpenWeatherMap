package com.asryab.openweathermap.des;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.Coords;
import com.asryab.openweathermap.data.ForecastDay;
import com.asryab.openweathermap.data.ForecastWeather;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ForecastWeatherDes implements JsonDeserializer<ForecastWeather>
{
    @Override
    public ForecastWeather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        ForecastWeather fw = new ForecastWeather();
        JsonObject rootObject = json.getAsJsonObject();
        JsonObject city = rootObject.get("city").getAsJsonObject();
        fw.setCity(city.get("name").getAsString());
        fw.setCountry(city.get("country").getAsString());
        fw.setCoords((Coords) context.deserialize(city.get("coord"), Coords.class));

        JsonArray days = rootObject.get("list").getAsJsonArray();
        List<ForecastDay> list = new ArrayList<ForecastDay>();
        for (JsonElement day: days)
        {
            list.add((ForecastDay) context.deserialize(day, ForecastDay.class));
        }
        fw.setDaysList(list);

        return fw;
    }
}
