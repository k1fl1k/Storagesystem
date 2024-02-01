package com.k1fl1k.storagesystem.util;

/**
 * Клас, що надає функціональність для очищення консолі.
 */
public class ClearConsole {

    /**
     * Метод для очищення консолі.
     */
    public static void clearConsole() {
        try {
            // Визначення операційної системи
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                // Якщо операційна система - Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Якщо операційна система - UNIX-подібна
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            // Обробка можливих винятків
            e.printStackTrace();
        }
    }
}
