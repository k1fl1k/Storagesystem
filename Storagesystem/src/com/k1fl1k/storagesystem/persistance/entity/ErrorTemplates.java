package com.k1fl1k.storagesystem.persistance.entity;

public enum ErrorTemplates {
    REQUIRED("Поле %s є обов'язковим до заповнення."),
    MIN_LENGTH("Поле %s не може бути меншим за %d симв."),
    MAX_LENGTH("Поле %s не може бути більшим за %d симв."),
    ONLY_LATIN("Поле %s лише латинські символи та символ _."),
    PASSWORD(
        "Поле %s латинські миволи, хочаб одна буква з великої, одна з малої та хочаб одна цифра."),
    BIRTHDAY_RANGE("Поле %s повинно бути в межах від %s до %s."),
    PHONE_NUMBER("Поле %s повинно бути типу +1234567890");


    private String template;

    ErrorTemplates(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
