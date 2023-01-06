package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите путь до папки: ");
        String path = in.nextLine();
        System.out.print("Введите путь до файла вывода: ");
        String answerFile = in.nextLine();
        if (checkExistence(path, answerFile)) {
            try {
                FileWorker.working(path, answerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("Ошибка.");
        }
    }

    private static boolean checkExistence(String folderPath, String answerFile) {
        boolean folderExistence = false;
        Path path = Paths.get(folderPath);
        Path file = Paths.get(answerFile);
        if (!Files.exists(file)) {
            System.out.println("Файла вывода не существует. Создаем.");
        }
        if (Files.exists(path)) {
            folderExistence = true;
        } else {
            System.out.println("Проблемы с директорией или доступом.");
        }
        return folderExistence;
    }
}

