package com.k1fl1k.storagesystem.mainmenu;

import static com.k1fl1k.storagesystem.util.ClearConsole.clearConsole;
import static java.lang.System.out;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.k1fl1k.storagesystem.persistance.entity.impl.Storage;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

/**
 * Клас Menu представляє головне меню системи зберiгання. Надає iнтерфейс користувача для взаємодiї
 * з елементами зберiгання та ревiзiй.
 */
public class Menu {

    /**
     * Завантажує данi про зберiгання з JSON-файлу.
     *
     * @param filePath Шлях до JSON-файлу iз даними про зберiгання.
     * @return Список елементiв зберiгання, завантажених з JSON-файлу.
     */
    public static List<Storage> loadStorageDataFromJson(String filePath) {
        List<Storage> storages;

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            Type storageListType = new TypeToken<List<Storage>>() {
            }.getType();

            storages = gson.fromJson(reader, storageListType);
        } catch (IOException e) {
            System.err.println("Помилка завантаження даних з JSON: " + e.getMessage());
            storages = List.of();
        }

        return storages;
    }

    /**
     * Вiдображає головне меню та обробляє вибiр користувача.
     *
     * @param username iм'я користувача, який увiйшов в систему.
     */
    public void menu(String username) {
        List<Storage> storages = loadStorageDataFromJson("Data/storages.json");
        for (int i = 1; i > 0; ) {
            clearConsole();
            out.println("Вiтаю " + username);
            out.println("Оберiть наступну дiю");
            out.println(
                "1. Вивести всю iнформацiю про склади. \n2. Додати новий склад. \n3. Видалити склад."
                    + "\n4. Вивести всi ревiзiї.\n5. Додати нову ревiзiю. \n6. Видалити одну ревiзiю."
                    + " \n0. Вихiд.");
            Scanner scanner = new Scanner(System.in);
            int choise = scanner.nextInt();
            WorkWithStorages workWithStorages = new WorkWithStorages();
            WorkWithAuditors workWithAuditors = new WorkWithAuditors();
            switch (choise) {
                case 1:
                    workWithStorages.printAllStorages("Data/storages.json");
                    break;
                case 2:
                    workWithStorages.addNewStorage(storages);
                    break;
                case 3:
                    workWithStorages.deleteStorageFromFile(storages, "Data/storages.json");
                    break;
                case 4:
                    workWithAuditors.printAllAuditors("Data/auditors.json");
                    break;
                case 5:
                    workWithAuditors.addNewAuditor();
                    break;
                case 6:
                    workWithAuditors.deleteAuditorFromFile("Data/auditors.json");
                    break;
                default:
                    out.println("Гарного дня!");
                    i -= 1;
                    break;
            }
        }
    }
}
