package com.k1fl1k.storagesystem.persistance.entity.impl;

import com.k1fl1k.storagesystem.persistance.entity.Entity;
import com.k1fl1k.storagesystem.persistance.entity.ErrorTemplates;
import com.k1fl1k.storagesystem.persistance.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Клас, що представляє аудитора в системі.
 */
public class Auditor extends Entity {

    /**
     * День аудиту.
     */
    private final LocalDate auditionDay;

    /**
     * Назва сховища, яке перевіряється аудитором.
     */
    private final String auditedStorageName;

    /**
     * Ім'я аудитора.
     */
    private String auditorName;

    /**
     * Конструктор класу {@code Auditor}, який ініціалізує всі поля аудитора.
     *
     * @param id             унікальний ідентифікатор аудитора
     * @param auditionDay    день аудиту
     * @param auditedStorage назва сховища, яке перевіряється аудитором
     * @param auditorName    ім'я аудитора
     */
    public Auditor(UUID id, LocalDate auditionDay, String auditedStorage, String auditorName) {
        super(id);
        this.auditedStorageName = auditedStorage;
        this.auditionDay = auditionDay;
        setAuditorName(auditorName);
    }

    /**
     * Метод для отримання ім'я аудитора.
     *
     * @return ім'я аудитора
     */
    public String getAuditorName() {
        return auditorName;
    }

    /**
     * Метод для встановлення ім'я аудитора з валідацією.
     *
     * @param auditorName ім'я аудитора
     */
    public void setAuditorName(String auditorName) {
        final String templateName = "імені аудитора";

        if (auditorName.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.auditorName = auditorName;
    }

    /**
     * Метод для отримання дня аудиту.
     *
     * @return день аудиту
     */
    public LocalDate getAuditionDay() {
        return auditionDay;
    }

    /**
     * Перевизначений метод для порівняння аудиторів за днем аудиту, назвою сховища та іменем
     * аудитора.
     *
     * @param o інший об'єкт для порівняння
     * @return true, якщо аудитори рівні, false - в іншому випадку
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
        Auditor auditor = (Auditor) o;
        return Objects.equals(auditionDay, auditor.auditionDay) && Objects.equals(
            auditedStorageName, auditor.auditedStorageName) && Objects.equals(auditorName,
            auditor.auditorName);
    }

    /**
     * Перевизначений метод для обчислення хеш-коду аудитора на основі дня аудиту, назви сховища та
     * імені аудитора.
     *
     * @return хеш-код аудитора
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), auditionDay, auditedStorageName, auditorName);
    }

    /**
     * Перевизначений метод для отримання рядкового представлення аудитора.
     *
     * @return рядкове представлення аудитора
     */
    @Override
    public String toString() {
        return "Auditor{" +
            "auditionDay=" + auditionDay +
            ", auditedStorageName='" + auditedStorageName + '\'' +
            ", auditorName='" + auditorName + '\'' +
            ", id=" + id +
            '}';
    }
}
