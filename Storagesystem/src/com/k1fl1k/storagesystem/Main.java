package com.k1fl1k.storagesystem;

import static java.lang.System.exit;
import static java.lang.System.out;

import com.k1fl1k.storagesystem.util.Authorisation;
import com.k1fl1k.storagesystem.util.Registration;
import java.util.Scanner;

/**
 * Клас Main є точкою входу для виконання програми. Він надає вибір між авторизацією та реєстрацією
 * користувача.
 */

public class Main {

    /**
     * Точка входу для виконання програми. Користувачу пропонується вибрати між авторизацією та
     * реєстрацією.
     *
     * @param args аргументи командного рядка (не використовуються у цьому випадку)
     */
    public static void main(String[] args) {
        String data = "Data/";

        // Вивід вітального повідомлення та варіантів вибору
        out.println("Вiтаю!");

        boolean loop = true;

        while (loop == true) {
            out.println("\nОберiть Ваш наступний крок! \n1 Авторизацiя. \n2 Реєстрацiя."
                + "\n0 Вихiд.");
            // Читання вибору користувача з консолі
            Scanner scanner = new Scanner(System.in);
            int choise = scanner.nextInt();

            // Обробка вибору користувача за допомогою оператора switch
            switch (choise) {
                case 1:
                    // Виклик методу авторизації, якщо користувач обрав 1
                    Authorisation authorisation = new Authorisation();
                    authorisation.authorisation(data + "users.json");
                    break;
                case 2:
                    // Виклик методу реєстрації, якщо користувач обрав 2
                    Registration registration = new Registration();
                    registration.registration(data + "users.json");
                    break;
                case 0:
                    out.println("До зустрiчi");
                    exit(0);
                default:
                    // Вихід з програми, якщо користувач ввів невірний вибір
                    out.println("Спробуйте ще раз!");
            }
        }
    }
}
