package ru.liga.forecastor.util.reader;

import ru.liga.forecastor.model.type.Range;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadCsv {

    public static List<String> readCsv(String path, Range readRange) {
        try {
            List<String> dataFromCsv = new ArrayList<>();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream(path);
            InputStreamReader streamReader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader csvReader = new BufferedReader(streamReader);
            csvReader.readLine(); //чтение первой строки с Headers
            for (int i = 0; i < readRange.getValue(); i++) {
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
