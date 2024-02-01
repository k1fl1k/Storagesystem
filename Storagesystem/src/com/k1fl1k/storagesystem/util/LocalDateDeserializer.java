package com.k1fl1k.storagesystem.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Клас, що реалізує JsonDeserializer для LocalDate.
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    /**
     * Метод, який десеріалізує JsonElement у LocalDate, використовуючи рядок з JsonPrimitive.
     *
     * @param json    JsonElement, який треба десеріалізувати
     * @param typeOfT тип, із яким працює Gson (не використовується у цьому випадку)
     * @param context контекст десеріалізації (не використовується у цьому випадку)
     * @return LocalDate, який представляє десеріалізоване значення
     * @throws JsonParseException якщо виникає помилка під час десеріалізації
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
    }
}
