package com.asryab.openweathermap.des;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.WindParameters;

import java.lang.reflect.Type;

public class WindParametersDes implements JsonDeserializer<WindParameters>
{
    @Override
    public WindParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        WindParameters params = new WindParameters();
        params.setDeg(jsonObject.get("deg").getAsInt());
        params.setSpeed(jsonObject.get("speed").getAsDouble());
        return params;
    }
}
