package com.k1fl1k.storagesystem.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.k1fl1k.storagesystem.mainmenu.Menu;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Клас, що відповідає за авторизацію користувача.
 */
public class Authorisation {

    /**
     * Метод для авторизації користувача з використанням даних з файлу JSON.
     *
     * @param fileName шлях до файлу, в якому зберігаються дані про користувачів
     */
    public void authorisation(String fileName) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Зчитуємо JSON з файлу
            JsonArray jsonArray = new Gson().fromJson(new FileReader(fileName), JsonArray.class);

            // Введення ім'я користувача та пароля
            System.out.print("Введiть username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Введiть пароль: ");
            String enteredPassword = scanner.nextLine();

            // Проходимо по всіх об'єктах масиву
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Отримуємо інформацію з JSON
                String storedUsername = jsonObject.get("username").getAsString();
                String storedPassword = jsonObject.get("password").getAsString();

                // Перевірка введених даних
                if (enteredUsername.equals(storedUsername) && enteredPassword.equals(
                    storedPassword)) {
                    System.out.println("Авторизацiя успiшна!");
                    Menu menu = new Menu();
                    menu.menu(enteredUsername);
                    return;
                }
            }
            System.out.println("Авторизацiя невдала. Неправильний username або пароль.");
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
