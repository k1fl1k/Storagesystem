package com.k1fl1k.storagesystem.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Клас, що реалізує TypeAdapter для LocalDate.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Форматтер для перетворення LocalDate в рядок та з рядка у LocalDate.
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Метод, який записує LocalDate у форматі рядка у JsonWriter.
     *
     * @param out  JsonWriter, у який записується LocalDate
     * @param date LocalDate, який треба записати
     * @throws IOException якщо виникає помилка під час запису
     */
    @Override
    public void write(JsonWriter out, LocalDate date) throws IOException {
        if (date == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(date));
        }
    }

    /**
     * Метод, який зчитує рядок з JsonReader та перетворює його у LocalDate.
     *
     * @param in JsonReader, з якого зчитується LocalDate у форматі рядка
     * @return LocalDate, який був зчитаний
     * @throws IOException якщо виникає помилка під час зчитування
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String dateString = in.nextString();
            return LocalDate.parse(dateString, formatter);
        }
    }
}
