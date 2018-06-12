package com.feng.accounts.adapter.messaging;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

/**
 * Created by minggaoxi on 17/07/2017.
 */
@Slf4j
public class EventReader {

    private JsonObject representation;

    public EventReader(String jsonSerialization) {
        this.representation = new JsonReader().deserialize(jsonSerialization);
    }

    protected EventReader(JsonObject representation) {
        this.representation = representation;
    }

    EventReader() {}

    public Optional<String> stringValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsString);
    }

    public Optional<Integer> intValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsInt);
    }

    public Optional<Long> longValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsLong);
    }

    public Optional<Double> doubleValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsDouble);
    }

    public Optional<Float> floatValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsFloat);
    }

    public Optional<Boolean> booleanValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsBoolean);
    }

    public Optional<Instant> instantValue(String... keys) {
        return this.navigateTo(this.representation, keys).map(JsonElement::getAsLong).map(Instant::ofEpochMilli);
    }

    public <K, V> Optional<Map<K, V>> mapValue(String... keys) {
        return this.navigateTo(this.representation, keys)
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> new Gson().fromJson(jsonObject, new TypeToken<Map>(){}.getType()));
    }

    protected Optional<JsonElement> navigateTo(JsonObject jsonObject, String... keys) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("Must specify one or more keys.");
        } else if (keys.length == 1 && (keys[0].startsWith("/") || keys[0].contains("."))) {
            keys = this.parsePath(keys[0]);
        }

        int keyIndex = 1;
        JsonElement element = this.elementFrom(jsonObject, keys[0]);
        if (element != null && !element.isJsonNull() && !element.isJsonPrimitive() && !element.isJsonArray()) {
            JsonObject object = element.getAsJsonObject();

            for (; element != null && !element.isJsonPrimitive() && keyIndex < keys.length; ++keyIndex) {
                element = this.elementFrom(object, keys[keyIndex]);

                if (!element.isJsonPrimitive()) {
                    if (element.isJsonNull()) {
                        element = null;
                    } else {
                        object = element.getAsJsonObject();
                    }
                }
            }
        }

        if (element != null) {
            if (!element.isJsonNull()) {
                if (keyIndex != keys.length) {
                    throw new IllegalArgumentException("Last name must reference a simple value.");
                }
            } else {
                element = null;
            }
        }

        return Optional.ofNullable(element);
    }

    protected JsonElement elementFrom(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);

        if (element == null) {
            element = jsonObject.get("@" + key);
        }

        return element;
    }

    private String[] parsePath(String propertiesPath) {
        boolean startsWithSlash = propertiesPath.startsWith("/");
        return startsWithSlash ? propertiesPath.substring(1).split("/") : propertiesPath.split("\\.");
    }

    private static class JsonReader {

        protected JsonObject deserialize(String serialization) {
            try {
                JsonObject jsonObject = new JsonParser().parse(serialization).getAsJsonObject();
                return jsonObject;
            } catch (Exception e) {
                log.error("Parse json serialization '{}' failed.", serialization);
                throw new RuntimeException(e);
            }
        }

    }

}
