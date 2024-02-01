package com.k1fl1k.storagesystem.persistance.exception;

/**
 * Виняток, який виникає, коли сутність не знайдена.
 */
public class EntityNotFoundExeption extends RuntimeException {

    /**
     * Конструктор класу, який приймає повідомлення про помилку.
     *
     * @param message повідомлення про помилку
     */
    public EntityNotFoundExeption(String message) {
        super(message);
    }

}
