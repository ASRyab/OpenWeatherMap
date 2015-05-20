package com.asryab.openweathermap.des;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.Coords;

import java.lang.reflect.Type;


public class CoordsDes implements JsonDeserializer<Coords>
{
    @Override
    public Coords deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        Coords coords = new Coords();
        coords.setLantitude(jsonObject.get("lat").getAsDouble());
        coords.setLongitude(jsonObject.get("lon").getAsDouble());
        return coords;
    }
}
