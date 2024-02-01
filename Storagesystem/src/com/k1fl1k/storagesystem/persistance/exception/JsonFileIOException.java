package com.k1fl1k.storagesystem.persistance.exception;

/**
 * Виняток, який виникає при помилках роботи з JSON-файлами.
 */
public class JsonFileIOException extends RuntimeException {

    /**
     * Конструктор класу, який приймає повідомлення про помилку.
     *
     * @param message повідомлення про помилку
     */
    public JsonFileIOException(String message) {
        super(message);
    }
}
