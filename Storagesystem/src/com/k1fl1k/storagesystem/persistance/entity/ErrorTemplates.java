package com.k1fl1k.storagesystem.persistance.entity;

/**
 * Перелiк шаблонiв помилок для валiдацiї полiв сутностей.
 */
public enum ErrorTemplates {
    REQUIRED("Поле %s є обов'язковим до заповнення."),
    MIN_LENGTH("Поле %s не може бути меншим за %d симв."),
    MAX_LENGTH("Поле %s не може бути бiльшим за %d симв."),
    ONLY_LATIN("Поле %s лише латинськi символи та обов'язковий символ\".\"."),
    EMAIL_VALID("Поле %s лише латинськi символи та обов'язковий символ @ з текстом пiсля."),
    PASSWORD(
        "Поле %s латинськi символи, хочаб одна буква з великої, одна з малої та хочаб одна цифра."),
    BIRTHDAY_RANGE("Поле %s повинно бути в межах вiд %s до %s."),
    PHONE_NUMBER("Поле %s повинно бути типу +1234567890"),
    BIRTHDAY("Поле має бути формату РРРР-ММ-ДД");

    /**
     * Шаблон повiдомлення про помилку.
     */
    private final String template;

    /**
     * Конструктор класу, який приймає шаблон повiдомлення про помилку.
     *
     * @param template шаблон повiдомлення про помилку
     */
    ErrorTemplates(String template) {
        this.template = template;
    }

    /**
     * Метод для отримання шаблону повiдомлення про помилку.
     *
     * @return шаблон повiдомлення про помилку
     */
    public String getTemplate() {
        return template;
    }
}
