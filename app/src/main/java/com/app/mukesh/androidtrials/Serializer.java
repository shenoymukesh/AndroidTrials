
package com.app.mukesh.androidtrials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;


public class Serializer {

    public static Object fromJSON(String jsonString, Type parentType) {
        Gson gson = getGson();
        return gson.fromJson(jsonString, parentType);
    }

    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        HashMap<Type, JsonDeserializer<?>> adapterMap =
                new HashMap<Type, JsonDeserializer<?>>();
        Type type = new TypeToken<MediaModel>() {
        }.getType();
        adapterMap.put(type, new MediaModelAdapter());
        for (Type key : adapterMap.keySet()) {
            gsonBuilder.registerTypeAdapter(key, adapterMap.get(key));
        }

        return gsonBuilder.create();
    }
}
