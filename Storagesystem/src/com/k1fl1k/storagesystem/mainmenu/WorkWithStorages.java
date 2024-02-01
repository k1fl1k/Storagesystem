package com.k1fl1k.storagesystem.mainmenu;

import static java.lang.System.out;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.k1fl1k.storagesystem.persistance.entity.impl.Storage;
import com.k1fl1k.storagesystem.persistance.exception.EntityArgumentException;
import com.k1fl1k.storagesystem.util.LocalDateSerializer;
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
 * Клас, що здiйснює операцiї зi сховищами в головному меню.
 */
public class WorkWithStorages {

    /**
     * Шлях до файлу, де зберiгаються данi про сховища.
     */
    private String filePath = "Data/storages.json";

    /**
     * Завантажує данi про сховища з JSON файлу.
     *
     * @param filePath шлях до JSON файлу
     * @return список сховищ
     */
    public static List<Storage> loadDataFromJson(String filePath) {
        List<Storage> storages;

        try (FileReader reader = new FileReader(filePath)) {
            // Створюємо iнстанцiю Gson
            Gson gson = new Gson();

            // Визначаємо тип для десерiалiзацiї
            Type storageListType = new TypeToken<List<Storage>>() {
            }.getType();

            // Читаємо вмiст JSON файлу та десерiалiзуємо його у список сховищ
            storages = gson.fromJson(reader, storageListType);
        } catch (IOException e) {
            // Обробка виняткiв (наприклад, файл не знайдено, проблеми з форматом JSON)
            System.err.println("Error loading data from JSON: " + e.getMessage());
            storages = List.of(); // Альтернативно, можна iнiцiалiзувати порожнiм списком
        }

        return storages;
    }

    /**
     * Виводить на екран iнформацiю про всi сховища.
     */
    public void showStorage(JsonObject storageObject) {
        System.out.println("\nIм'я: " + getStringFieldValue(storageObject, "storageName"));
        System.out.println("Адреса: " + getStringFieldValue(storageObject, "address"));
        System.out.println("Адмiнiстратор: " + getStringFieldValue(storageObject, "admin"));
    }

    /**
     * Додає нове сховище до списку та зберiгає його у JSON файлi.
     *
     * @param storages список сховищ
     */
    public void addNewStorage(List<Storage> storages) {

        Scanner scanner = new Scanner(System.in);
        try {
            UUID storageId = UUID.randomUUID();
            System.out.print("Введiть адресу: ");
            String storageAddress = scanner.nextLine();
            System.out.print("Введiть назву сховища: ");
            String storageName = scanner.nextLine();
            System.out.print("Введiть адмiнiстратора сховища: ");
            String storageAdmin = scanner.nextLine();

            Storage storage = new Storage(storageId, storageAddress, storageName,
                storageAdmin);
            storages.add(storage);

            writeStoragesListToJsonFile(storages, filePath);

        } catch (EntityArgumentException e) {
            for (String error : e.getErrors()) {
                System.out.println("Error: " + error);
            }
        }
        out.println("\nЩоб повернутись на головне меню настиснiть будь-яку кнопку");
        scanner.nextLine();
    }

    /**
     * Записує список сховищ у JSON файл.
     *
     * @param storages список сховищ
     * @param filePath шлях до JSON файлу
     */
    public void writeStoragesListToJsonFile(List<Storage> storages, String filePath) {
        loadDataFromJson(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();

            gson.toJson(storages, writer);

            System.out.println("Сховища збережено в файл " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Видаляє сховище зi списку та зберiгає оновлений список у JSON файлi.
     *
     * @param storages список сховищ
     * @param filePath шлях до JSON файлу
     */
    public void deleteStorageFromFile(List<Storage> storages, String filePath) {
        loadDataFromJson(filePath);
        Scanner scanner = new Scanner(System.in);

        out.println("Введiть назву склада");
        String storageToDelete = scanner.nextLine();

        List<Integer> indicesToDelete = new ArrayList<>();

        for (int i = 0; i < storages.size(); i++) {
            if (storages.get(i).getName().equals(storageToDelete)) {
                indicesToDelete.add(i);
            }
        }

        if (!indicesToDelete.isEmpty()) {
            for (int i : indicesToDelete) {
                storages.remove(i);
            }
            System.out.println("Сховища успiшно видалено.");

            out.println("\nЩоб повернутись на головне меню настиснiть будь-яку кнопку");
            scanner.nextLine();
            // Зберегти оновлений список у JSON файлi
            writeStoragesListToJsonFile(storages, filePath);
        } else {
            System.out.println("Сховища з такою назвою не знайдено.");

            out.println("\nЩоб повернутись на головне меню настиснiть будь-яку кнопку");
            scanner.nextLine();
        }
    }

    /**
     * Виводить iнформацiю про топових гравцiв з файлу JSON.
     *
     * @param filePath Шлях до файлу JSON з iнформацiєю про топових гравцiв.
     */
    public void printAllStorages(String filePath) {
        try {
            // Зчитування JSON з файлу як масив
            JsonArray jsonArray = new Gson().fromJson(new FileReader(filePath), JsonArray.class);

            if (jsonArray != null) {
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject storageObject = jsonElement.getAsJsonObject();
                    showStorage(storageObject);
                }
            } else {
                System.out.println("Помилка читання файлу або файл порожнiй.");
            }
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        out.println("\nЩоб повернутись на головне меню настиснiть будь-яку кнопку");
        scanner.nextLine();
    }

    /**
     * Отримує значення рядкового поля з JSON-об'єкта за iменем поля.
     *
     * @param jsonObject JSON-об'єкт.
     * @param fieldName  iм'я поля.
     * @return Значення рядкового поля або порожнiй рядок, якщо поле не iснує або не є примiтивом
     * JSON.
     */
    private String getStringFieldValue(JsonObject jsonObject, String fieldName) {
        JsonElement jsonElement = jsonObject.get(fieldName);
        if (jsonElement != null && jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        return ""; // Або iнше значення за замовчуванням, якщо поле вiдсутнє або не є примiтивом JSON
    }
}