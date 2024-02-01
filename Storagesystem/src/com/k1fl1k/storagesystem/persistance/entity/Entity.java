package com.k1fl1k.storagesystem.persistance.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Абстрактний клас, що представляє сутність з ідентифікатором UUID та функціональністю валідації.
 */
public class Entity {

    /**
     * Унікальний ідентифікатор сутності.
     */
    protected final UUID id;

    /**
     * Список повідомлень про помилки валідації сутності.
     */
    protected List<String> errors;

    /**
     * Прапорець, що вказує на валідність сутності.
     */
    protected boolean isValid;

    /**
     * Захищений конструктор для створення екземпляра сутності із заданим ідентифікатором.
     *
     * @param id унікальний ідентифікатор сутності
     */
    protected Entity(UUID id) {
        errors = new ArrayList<>();
        this.id = id;
    }

    /**
     * Метод для отримання унікального ідентифікатора сутності.
     *
     * @return унікальний ідентифікатор сутності
     */
    public UUID getId() {
        return id;
    }

    /**
     * Метод для перевірки валідності сутності.
     *
     * @return true, якщо сутність валідна, false - в іншому випадку
     */
    public boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Метод для отримання списку повідомлень про помилки валідації сутності.
     *
     * @return список повідомлень про помилки
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Перевизначений метод для порівняння сутності за ідентифікатором.
     *
     * @param o інший об'єкт для порівняння
     * @return true, якщо ідентифікатори рівні, false - в іншому випадку
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    /**
     * Перевизначений метод для обчислення хеш-коду сутності на основі ідентифікатора.
     *
     * @return хеш-код сутності
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
