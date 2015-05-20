package com.asryab.openweathermap.des;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.Coords;
import com.asryab.openweathermap.data.CurrentWeather;
import com.asryab.openweathermap.data.MainParameters;
import com.asryab.openweathermap.data.StateParameters;
import com.asryab.openweathermap.data.WindParameters;

import java.lang.reflect.Type;

public class CurrentWeatherDes implements JsonDeserializer<CurrentWeather>
{
    @Override
    public CurrentWeather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject rootObject = json.getAsJsonObject();
        CurrentWeather cw = new CurrentWeather();

        cw.setDate(rootObject.get("dt").getAsLong());
        cw.setCity(rootObject.get("name").getAsString());
        
        JsonObject sysObject = rootObject.get("sys").getAsJsonObject();
        cw.setCountry(sysObject.get("country").getAsString());
        cw.setSunrise(sysObject.get("sunrise").getAsLong());
        cw.setSunset(sysObject.get("sunset").getAsLong());

        JsonElement stateParams = rootObject.get("weather").getAsJsonArray().get(0);
        cw.setStateParams((StateParameters) context.deserialize(
                stateParams, StateParameters.class));

        JsonElement mainParams = rootObject.get("main");
        cw.setMainParams((MainParameters) context.deserialize(
                mainParams, MainParameters.class));

        JsonElement windParams = rootObject.get("wind");
        cw.setWindParams((WindParameters) context.deserialize(
                windParams, WindParameters.class));

        JsonElement coords = rootObject.get("coord");
        cw.setCoords((Coords)context.deserialize(coords, Coords.class));

        return cw;
    }
}
