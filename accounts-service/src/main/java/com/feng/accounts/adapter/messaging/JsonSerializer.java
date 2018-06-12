package com.feng.accounts.adapter.messaging;

import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Created by minggaoxi on 30/03/2017.
 */
@Component
public class JsonSerializer {

    private Gson gson;

    public JsonSerializer() {
        this.build();
    }

    public String serialize(Object object) {
        return this.gson.toJson(object);
    }

    public <T> T deserialize(String serialization, Class<T> type) {
        return this.gson.fromJson(serialization, type);
    }

    private void build() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantSerializer())
                .registerTypeAdapter(Instant.class, new InstantDeserializer())
                .serializeNulls().create();
    }

    private class InstantSerializer implements com.google.gson.JsonSerializer<Instant> {

        @Override
        public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toEpochMilli());
        }

    }

    private class InstantDeserializer implements JsonDeserializer {

        @Override
        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
        }

    }

}
