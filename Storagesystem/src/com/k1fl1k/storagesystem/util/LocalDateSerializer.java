package com.k1fl1k.storagesystem.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Клас, що реалізує JsonSerializer для LocalDate.
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    /**
     * Форматтер для перетворення LocalDate в рядок.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Метод, який серіалізує LocalDate у вигляді рядка за допомогою JsonPrimitive.
     *
     * @param localDate                LocalDate, який треба серіалізувати
     * @param type                     тип, із яким працює Gson (не використовується у цьому
     *                                 випадку)
     * @param jsonSerializationContext контекст серіалізації (не використовується у цьому випадку)
     * @return JsonElement, який представляє LocalDate у вигляді рядка
     */
    @Override
    public JsonElement serialize(LocalDate localDate, Type type,
        JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}
