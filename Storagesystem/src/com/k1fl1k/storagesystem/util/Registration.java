package com.k1fl1k.storagesystem.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.k1fl1k.storagesystem.mainmenu.Menu;
import com.k1fl1k.storagesystem.persistance.entity.impl.User;
import com.k1fl1k.storagesystem.persistance.exception.EntityArgumentException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Клас, що вiдповiдає за реєстрацiю нових користувачiв.
 */
public class Registration {

    /**
     * Зчитує користувачiв з файлу та повертає їх у виглядi списку.
     *
     * @param filePath шлях до файлу, з якого зчитуються користувачi
     * @return список користувачiв
     */
    public static List<User> readUsersFromFile(String filePath) {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Створюємо Gson з можливiстю десерiалiзацiї LocalDate
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();

            // Визначаємо тип для десерiалiзацiї списку користувачiв
            Type userListType = new TypeToken<List<User>>() {
            }.getType();

            // Читаємо вмiст файлу та десерiалiзуємо його у список користувачiв
            userList = gson.fromJson(reader, userListType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    /**
     * Записує список користувачiв у JSON файл.
     *
     * @param users    список користувачiв для запису
     * @param filePath шлях до файлу, у який записується список користувачiв
     */
    private static void writeUserListToJsonFile(List<User> users, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Створюємо Gson з красивим виведенням
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();

            // Перетворюємо список користувачiв в JSON та записуємо у файл
            gson.toJson(users, writer);

            System.out.println("Користувачiв збережено в файл " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для реєстрацiї нового користувача.
     *
     * @param filePath шлях до файлу, в який записується новий користувач
     */
    public void registration(String filePath) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = readUsersFromFile(filePath);

        try {
            // Введення даних користувача
            UUID userId = UUID.randomUUID();
            System.out.print("Введiть username: ");
            String username = scanner.nextLine();
            System.out.print("Введiть пароль: ");
            String password = scanner.nextLine();
            System.out.print("Введiть email: ");
            String email = scanner.nextLine();
            System.out.print("Введiть дату нароження (рррр-мм-дд): ");
            LocalDate birthday = LocalDate.parse(scanner.nextLine());

            if (userAlreadyExists(users, email, username)) {
                System.out.println("Помилка: Користувач з таким username або email вже iснує.\n");
                registration(filePath);
            }

            // Створення об'єкта користувача та додавання його до списку
            User user = new User(userId, password, email, birthday, username);
            users.add(user);

            // Перезапис файлу JSON з оновленим списком користувачiв
            writeUserListToJsonFile(users, filePath);

            System.out.println("Реєстрацiя успiшна!");
            scanner.nextLine();
            Menu menu = new Menu();
            menu.menu(username);
        } catch (EntityArgumentException e) {
            // Обробка помилок, якщо вони виникли пiд час створення користувача
            for (String error : e.getErrors()) {
                System.out.println("Error: " + error);
            }
        }

    }

    /**
     * Перевiряє, чи iснує користувач з вказаним email або username в списку.
     *
     * @param users    список користувачiв
     * @param email    email користувача
     * @param username username користувача
     * @return true, якщо користувач з вказаним email або username iснує, iнакше - false
     */
    private boolean userAlreadyExists(List<User> users, String email, String username) {
        for (User user : users) {
            if (user.getEmail().equals(email) || user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
