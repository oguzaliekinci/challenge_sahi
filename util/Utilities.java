package com.sahibinden.challenge.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sahibinden.challenge.api.entities.Tweet;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Nonnull;

public class Utilities {

    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");


    @Nonnull
    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder = builder.disableHtmlEscaping();
        builder = builder.disableInnerClassSerialization();
        builder = builder.serializeNulls();

        builder.registerTypeHierarchyAdapter(Date.class, new UtcAdapter());

        builder = builder.setPrettyPrinting();
        return builder.create();
    }
    public static Gson getGsonForPrivateVariableClass() {
        return new GsonBuilder().setFieldNamingStrategy(field -> {
            String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
            return name.substring(2); // private fields are named as mName i.e m_name
        }).create();
    }
    private static class UtcAdapter implements JsonSerializer<Date>,
            JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

            if (json == null) {
                return null;
            }

            if (!json.isJsonPrimitive()) {
                return null;
            }

            final String s = json.getAsString();

            if ((s == null) || (s.length() < 1)) {
                return null;
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
                simpleDateFormat.setTimeZone(TIME_ZONE);
                Date date = simpleDateFormat.parse(s);

                return date;
            } catch (final ParseException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            if (src == null) {
                return null;
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    DATE_FORMAT_STRING);
            simpleDateFormat.setTimeZone(TIME_ZONE);
            return new JsonPrimitive(simpleDateFormat.format(src));

        }

    }
    public static String retrieveNextResultId(ArrayList<Tweet> tweetList){

        String requiredString =  tweetList.get(tweetList.size() - 1).getId_str();

        return requiredString;
    }
}
