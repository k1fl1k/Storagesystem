package com.k1fl1k.storagesystem.persistance.exception;

import java.util.List;

/**
 * Виняток, який виникає при некоректних аргументах сутності з вказанням помилок валідації полів.
 */
public class EntityArgumentException extends IllegalArgumentException {

    /**
     * Список повідомлень про помилки, що описують проблеми валідації полів сутності.
     */
    private final List<String> errors;

    /**
     * Конструктор нового об'єкта {@code EntityArgumentException} із вказаним списком повідомлень
     * про помилки.
     *
     * @param errors список повідомлень про помилки, які вказують на проблеми валідації полів
     *               сутності
     */
    public EntityArgumentException(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Отримує список повідомлень про помилки, пов'язаних із цим винятком.
     *
     * @return список повідомлень про помилки
     */
    public List<String> getErrors() {
        return errors;
    }
}
