package com.k1fl1k.storagesystem.persistance.entity.impl;

import com.k1fl1k.storagesystem.persistance.entity.Entity;
import com.k1fl1k.storagesystem.persistance.entity.ErrorTemplates;
import java.util.Objects;
import java.util.UUID;

/**
 * Клас, що представляє сховище в системі.
 */
public class Storage extends Entity {

    /**
     * Назва сховища.
     */
    public final String storageName;

    /**
     * Адреса сховища.
     */
    private final String address;

    /**
     * Адміністратор сховища.
     */
    private String admin;

    /**
     * Конструктор класу {@code Storage}, який ініціалізує всі поля сховища.
     *
     * @param id      унікальний ідентифікатор сховища
     * @param address адреса сховища
     * @param name    назва сховища
     * @param admin   адміністратор сховища
     */
    public Storage(UUID id, String address, String name, String admin) {
        super(id);
        this.address = address;
        this.storageName = name;
        this.admin = admin;
    }

    /**
     * Метод для отримання адреси сховища.
     *
     * @return адреса сховища
     */
    public String getAddress() {
        return address;
    }

    /**
     * Метод для отримання назви сховища.
     *
     * @return назва сховища
     */
    public String getName() {
        return storageName;
    }

    /**
     * Метод для отримання адміністратора сховища.
     *
     * @return адміністратор сховища
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * Метод для встановлення адміністратора сховища з валідацією.
     *
     * @param admin адміністратор сховища
     * @return адміністратор сховища
     */
    public String setAdmin(String admin) {
        String templatename = "вмісту";

        if (admin.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templatename));
        }

        return admin;
    }

    /**
     * Перевизначений метод для порівняння сховищ за адресою, назвою та адміністратором.
     *
     * @param o інший об'єкт для порівняння
     * @return true, якщо сховища рівні, false - в іншому випадку
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Storage storage = (Storage) o;
        return Objects.equals(address, storage.address) && Objects.equals(storageName,
            storage.storageName) && Objects.equals(admin, storage.admin);
    }

    /**
     * Перевизначений метод для обчислення хеш-коду сховища на основі адреси, назви та
     * адміністратора.
     *
     * @return хеш-код сховища
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, storageName, admin);
    }

    /**
     * Перевизначений метод для отримання рядкового представлення сховища.
     *
     * @return рядкове представлення сховища
     */
    @Override
    public String toString() {
        return "Storage{" +
            "address='" + address + '\'' +
            ", name='" + storageName + '\'' +
            ", admin='" + admin + '\'' +
            ", id=" + id +
            '}';
    }
}
