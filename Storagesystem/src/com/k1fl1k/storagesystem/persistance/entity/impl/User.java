package com.k1fl1k.storagesystem.persistance.entity.impl;

import com.k1fl1k.storagesystem.persistance.entity.Entity;
import com.k1fl1k.storagesystem.persistance.entity.ErrorTemplates;
import com.k1fl1k.storagesystem.persistance.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Клас, що представляє користувача в системі.
 */
public class User extends Entity {

    /**
     * Пароль користувача.
     */
    private final String password;

    /**
     * Дата народження користувача.
     */
    private final LocalDate birthday;

    /**
     * Електронна пошта користувача.
     */
    private String email;

    /**
     * Логін користувача.
     */
    private String username;

    /**
     * Конструктор класу {@code User}, який ініціалізує всі поля користувача.
     *
     * @param id       унікальний ідентифікатор користувача
     * @param password пароль користувача
     * @param email    електронна пошта користувача
     * @param birthday дата народження користувача
     * @param username логін користувача
     */
    public User(UUID id, String password, String email, LocalDate birthday, String username) {
        super(id);
        this.password = validatedPassword(password);
        setEmail(email);
        this.birthday = validatedBirthday(birthday);
        setUsername(username);
    }

    /**
     * Метод для отримання пароля користувача.
     *
     * @return пароль користувача
     */
    public String getPassword() {
        return password;
    }

    /**
     * Метод для отримання електронної пошти користувача.
     *
     * @return електронна пошта користувача
     */
    public String getEmail() {
        return email;
    }

    /**
     * Метод для встановлення електронної пошти користувача з валідацією.
     *
     * @param email електронна пошта користувача
     */
    public void setEmail(String email) {
        final String templateName = "email";

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        var pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
        if (!pattern.matcher(email).matches()) {
            errors.add(ErrorTemplates.EMAIL_VALID.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.email = email;
    }

    /**
     * Метод для отримання дати народження користувача.
     *
     * @return дата народження користувача
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Метод для валідації дати народження користувача.
     *
     * @param birthday дата народження користувача
     * @return валідована дата народження користувача
     */
    public LocalDate validatedBirthday(LocalDate birthday) {
        final String templateName = "дня народження";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        var pattern = Pattern.compile("^\\d{4},\\d{2},\\d{2}$");
        if (pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.BIRTHDAY.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return birthday;
    }

    /**
     * Метод для отримання логіну користувача.
     *
     * @return логін користувача
     */
    public String getUsername() {
        return username;
    }

    /**
     * Метод для встановлення логіну користувача з валідацією.
     *
     * @param username логін користувача
     */
    public void setUsername(String username) {
        final String templateName = "логiну";

        if (username.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (username.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (username.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
        }
        var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (pattern.matcher(username).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.username = username;
    }

    /**
     * Метод для валідації та отримання валідованого паролю користувача.
     *
     * @param password пароль користувача
     * @return валідований пароль користувача
     */
    private String validatedPassword(String password) {
        final String templateName = "паролю";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (password.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 8));
        }
        if (password.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        var pattern = Pattern.compile("\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$\"");
        if (pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return password;
    }

    /**
     * Перевизначений метод для порівняння користувачів за електронною поштою.
     *
     * @param o інший об'єкт для порівняння
     * @return true, якщо електронні пошти рівні, false - в іншому випадку
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    /**
     * Перевизначений метод для отримання рядкового представлення користувача.
     *
     * @return рядкове представлення користувача
     */
    @Override
    public String toString() {
        return "User{" +
            "password='" + password + '\'' +
            ", birthday=" + birthday +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", id=" + id +
            '}';
    }

    /**
     * Перевизначений метод для обчислення хеш-коду користувача на основі електронної пошти.
     *
     * @return хеш-код користувача
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
