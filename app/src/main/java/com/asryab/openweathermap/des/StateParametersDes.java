package com.asryab.openweathermap.des;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.asryab.openweathermap.data.StateParameters;

import java.lang.reflect.Type;

public class StateParametersDes implements JsonDeserializer<StateParameters>
{
    @Override
    public StateParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        StateParameters params = new StateParameters();
        params.setIcon(jsonObject.get("icon").getAsString());
        params.setDescription(jsonObject.get("description").getAsString());
        return params;
    }
}
