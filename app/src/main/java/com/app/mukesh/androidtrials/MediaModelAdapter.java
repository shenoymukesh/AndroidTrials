package com.app.mukesh.androidtrials;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;

/**
 * Created by mukesh on 7/10/15.
 */
public class MediaModelAdapter implements JsonDeserializer<MediaModel> {

    @Override
    public MediaModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Class<? extends MediaModel> type = null;
        if (jsonObject.has("type")) {
            switch (jsonObject.get("type").getAsString()) {
                case "chapter":
                    type = Chapter.class;
                    break;
                case "mantra":
                case "aarti":
                    type = SongModel.class;
                    break;
            }
            return context.deserialize(json, type);
        }
        return null;
    }
}
