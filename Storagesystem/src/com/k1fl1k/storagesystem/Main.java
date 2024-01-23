package com.k1fl1k.storagesystem;

import static java.lang.System.out;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.k1fl1k.storagesystem.persistance.entity.impl.Storage;
import com.k1fl1k.storagesystem.persistance.entity.impl.User;
import com.k1fl1k.storagesystem.util.LocalDateSerializer;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        List<User> users = generateUsers(10);
        List<Storage> storages = generateStorages(10);
        // Виведемо створених користувачів
        for (User user : users) {
            out.println(user);
        }
        out.println("");
        for (Storage storage : storages) {
            out.println(storage);
        }
        writeUsersToJsonFile(users, "users.json");
        writeStorageToJsonFile(storages, "storages.json");
    }

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID userId = UUID.randomUUID();
            String password = faker.internet().password();
            String email = faker.internet().emailAddress();
            LocalDate birthday = faker.date()
                .birthday()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            String username = faker.name().username();
            String avatar = faker.internet().avatar();

            User user = new User(userId, password, email, birthday, username, avatar);
            users.add(user);
        }

        return users;
    }

    public static List<Storage> generateStorages(int count) {
        List<Storage> storages = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID storageId = UUID.randomUUID();
            String storageAddress = faker.address().streetAddress();
            String storageName = faker.name().title();
            String storageAdmin = faker.name().fullName();

            Storage storage = new Storage(storageId, storageAddress, storageName,
                storageAdmin);
            storages.add(storage);
        }

        return storages;
    }

    public static void writeUsersToJsonFile(List<User> users, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Створюємо Gson з красивим виведенням
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();

            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(users, writer);

            System.out.println("Колекцію користувачів збережено в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStorageToJsonFile(List<Storage> storages, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Створюємо Gson з красивим виведенням
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();

            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(storages, writer);

            System.out.println("Колекцію користувачів збережено в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

