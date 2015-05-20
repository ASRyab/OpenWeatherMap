package com.asryab.openweathermap.des;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.ForecastDay;
import com.asryab.openweathermap.data.StateParameters;
import com.asryab.openweathermap.data.TemperatureParameters;
import com.asryab.openweathermap.data.WindParameters;

import java.lang.reflect.Type;


public class ForecastDayDes implements JsonDeserializer<ForecastDay>
{
    @Override
    public ForecastDay deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        ForecastDay day = new ForecastDay();
        day.setTempParams((TemperatureParameters) context.deserialize(
                jsonObject.get("temp"), TemperatureParameters.class));
        day.setHumidity(jsonObject.get("humidity").getAsInt());
        day.setPressure(jsonObject.get("pressure").getAsDouble());
        day.setDate(jsonObject.get("dt").getAsLong());
        day.setWindParams((WindParameters) context.deserialize(json, WindParameters.class));
        day.setStateParams((StateParameters) context.deserialize(
                jsonObject.get("weather").getAsJsonArray().get(0), StateParameters.class));
        return day;
    }
}
