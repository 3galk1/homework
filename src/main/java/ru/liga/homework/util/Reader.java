package ru.liga.homework.util;

import ru.liga.homework.model.Command;
import ru.liga.homework.model.type.TimeRange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Reader {

    public String readCommand() {
        System.out.println("Введите команду:");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public List<String> readCsv(Command command) {
        try {
            List<String> dataFromCsv = new ArrayList<>();
            BufferedReader csvReader = new BufferedReader(new FileReader(command.getCurrency().getValue()));
            csvReader.readLine(); //чтение первой строки с Headers
            for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
                dataFromCsv.add(csvReader.readLine());
            }
            return dataFromCsv;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден " + e); // временно, будет заменен на ошибку
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + e); // временно, будет заменен на ошибку
            return null;
        }
    }
}