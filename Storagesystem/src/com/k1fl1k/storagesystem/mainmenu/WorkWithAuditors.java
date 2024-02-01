package com.k1fl1k.storagesystem.mainmenu;

import static java.lang.System.out;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.k1fl1k.storagesystem.persistance.entity.impl.Auditor;
import com.k1fl1k.storagesystem.persistance.entity.impl.Storage;
import com.k1fl1k.storagesystem.persistance.exception.EntityArgumentException;
import com.k1fl1k.storagesystem.util.LocalDateAdapter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Клас, який забезпечує роботу з аудиторами в головному меню.
 */
public class WorkWithAuditors {

    private static String nameFromJson;
    private String filePath = "Data/auditors.json";
    private List<Auditor> auditors;

    /**
     * Завантажує данi про аудиторiв з JSON файлу.
     *
     * @param filePath шлях до JSON файлу
     * @return список аудиторiв
     */
    public static List<Auditor> loadDataFromJson(String filePath) {
        List<Auditor> auditors;

        try (FileReader reader = new FileReader(filePath)) {
            // Створюємо iнстанцiю Gson з власним TypeAdapter для LocalDate
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

            // Визначаємо тип для десерiалiзацiї
            Type auditorListType = new TypeToken<List<Auditor>>() {
            }.getType();

            // Читаємо вмiст JSON файлу та десерiалiзуємо його у список аудиторiв
            auditors = gson.fromJson(reader, auditorListType);
        } catch (IOException e) {
            // Обробка виняткiв (наприклад, файл не знайдено, проблеми з форматом JSON)
            System.err.println("Error loading data from JSON: " + e.getMessage());
            auditors = List.of(); // Альтернативно, можна iнiцiалiзувати порожнiм списком
        }

        return auditors;
    }

    /**
     * Додає нового аудитора до списку та зберiгає його у JSON файлi.
     */
    public void addNewAuditor() {
        List<Auditor> auditors = loadDataFromJson(filePath);

        Scanner scanner = new Scanner(System.in);
        try {
            UUID id = UUID.randomUUID();
            System.out.print("Введiть дату ревiзiї (рррр-мм-дд): ");
            LocalDate auditionDay = LocalDate.parse(scanner.nextLine());
            System.out.print("Введiть назву складу: ");
            String auditedStorage = scanner.nextLine();
            if (!auditedStorage.equals(validateStorageName(auditedStorage))) {
                out.println("Такого складу не iснує"
                    + "Спробуйте ще раз");
            }
            System.out.print("Введiть iм'я аудитора: ");
            String auditorName = scanner.nextLine();

            Auditor auditor = new Auditor(id, auditionDay, auditedStorage, auditorName);
            auditors.add(auditor);

            writeAuditorsListToJsonFile(auditors, filePath);

        } catch (EntityArgumentException e) {
            for (String error : e.getErrors()) {
                System.out.println("Error: " + error);
            }
        }
    }

    /**
     * Видаляє аудитора зi списку та зберiгає оновлений список у JSON файлi.
     */
    public void deleteAuditorFromFile(String filePath) {
        List<Auditor> auditors = loadDataFromJson(filePath);
        Scanner scanner = new Scanner(System.in);

        out.println("Введiть iм'я аудитора");
        String auditorToDelete = scanner.nextLine();

        List<Auditor> updatedAuditors = new ArrayList<>();

        for (Auditor auditor : auditors) {
            if (!auditor.getAuditorName().equals(auditorToDelete)) {
                updatedAuditors.add(auditor);
            }
        }

        if (!updatedAuditors.isEmpty()) {
            System.out.println("Аудитора успiшно видалено.");

            // Зберегти оновлений список у JSON файлi
            writeAuditorsListToJsonFile(updatedAuditors, filePath);

            out.println("\nЩоб повернутись на головне меню настиснiть любу кнопку");
            scanner.nextLine();
        } else {
            System.out.println("Аудитора не знайдено.");

            out.println("\nЩоб повернутись на головне меню настиснiть любу кнопку");
            scanner.nextLine();
        }
    }

    /**
     * Записує список аудиторiв у JSON файл.
     *
     * @param auditors список аудиторiв
     * @param filePath шлях до JSON файлу
     */
    public void writeAuditorsListToJsonFile(List<Auditor> auditors, String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting().create();

            gson.toJson(auditors, writer);

            System.out.println("Аудиторiв збережено в файл " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Перевiряє валiднiсть назви сховища введеної користувачем.
     *
     * @param inputStorageName назва сховища, введена користувачем
     * @return валiдна назва сховища
     */
    public String validateStorageName(String inputStorageName) {
        List<String> storageNames = getStorageNames(); // Отримати назви сховищ з JSON файлу

        try {
            // Перевiрка всiх елементiв у списку storageNames
            for (String storageName : storageNames) {
                // Виклик методу для валiдацiї даних
                if (inputStorageName.equals(storageName)) {
                    return storageName; // Зупинка циклу, якщо iм'я знайдено
                }
            }

            // Виведення повiдомлення, якщо iм'я не знайдено
            System.out.println("Данi не є валiдними.");

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Виводить iнформацiю про топових гравцiв з файлу JSON.
     *
     * @param filePath Шлях до файлу JSON з iнформацiєю про топових гравцiв.
     */
    public void printAllAuditors(String filePath) {
        try {
            // Зчитування JSON з файлу як масив
            JsonArray jsonArray = new Gson().fromJson(new FileReader(filePath), JsonArray.class);

            if (jsonArray != null) {
                for (JsonElement jsonElement : jsonArray) {

                    JsonObject storageObject = jsonElement.getAsJsonObject();
                    showAuditors(storageObject);
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
     * Виводить iнформацiю про аудитора з JSON-об'єкта, включаючи iм'я, склад та дату аудиту.
     *
     * @param storageObject JSON-об'єкт, який мiстить iнформацiю про аудитора.
     */
    public void showAuditors(JsonObject storageObject) {
        System.out.println("\nIм'я: " + getStringFieldValue(storageObject, "auditorName"));
        System.out.println("Склад: " + getStringFieldValue(storageObject, "auditedStorageName"));
        System.out.println("Дата: " + getLocalDateFieldValue(storageObject, "auditionDay"));
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

    /**
     * Отримує значення дати з JSON-об'єкта за iменем поля та перетворює його у LocalDate.
     *
     * @param jsonObject JSON-об'єкт, з якого взяти значення дати.
     * @param fieldName  iм'я поля, що мiстить дату в рядковому форматi.
     * @return Об'єкт LocalDate, представляючий дату з JSON-об'єкта. Повертає null, якщо значення
     * поля вiдсутнє або не є примiтивом JSON.
     */
    private LocalDate getLocalDateFieldValue(JsonObject jsonObject, String fieldName) {
        String dateString = getStringFieldValue(jsonObject, fieldName);
        if (!dateString.isEmpty()) {
            // Вказуємо правильний формат дати
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, formatter);
        }
        return null; // або iнше значення за замовчуванням, якщо поле вiдсутнє або не є примiтивом JSON
    }

    /**
     * Отримує список назв сховищ з JSON файлу.
     *
     * @return список назв сховищ
     */
    public List<String> getStorageNames() {
        List<String> storageNames = new ArrayList<>();

        try (FileReader reader = new FileReader("Data/storages.json")) {
            // Створюємо iнстанцiю Gson
            Gson gson = new Gson();

            // Визначаємо тип для десерiалiзацiї
            Type storageListType = new TypeToken<List<Storage>>() {
            }.getType();

            // Читаємо вмiст JSON файлу та десерiалiзуємо його у JsonArray
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            // Видобуваємо назви сховищ з кожного JsonObject у JsonArray
            for (JsonElement element : jsonArray) {
                JsonObject storageObject = element.getAsJsonObject();

                // Перевiрка, чи iснує "storageName" у JsonObject i не є null
                if (storageObject.has("storageName") && !storageObject.get("storageName")
                    .isJsonNull()) {
                    String storageName = storageObject.getAsJsonPrimitive("storageName")
                        .getAsString();
                    storageNames.add(storageName);
                }
            }
        } catch (IOException e) {
            // Обробка виняткiв (наприклад, файл не знайдено, проблеми з форматом JSON)
            System.err.println("Error loading data from JSON: " + e.getMessage());
        }

        return storageNames;
    }
}
