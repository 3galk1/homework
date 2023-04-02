package ru.liga.forecaster.util.reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<String> readCsv(String path) throws IOException {
        try {
            List<String> dataFromCsv = new ArrayList<>();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream(path);
            InputStreamReader streamReader = new InputStreamReader(input , StandardCharsets.UTF_8);
            BufferedReader csvReader = new BufferedReader(streamReader);
            String row;
            while ((row = csvReader.readLine()) != null) {
                dataFromCsv.add(row);
            }
            dataFromCsv.remove(0);
            csvReader.close();
            return dataFromCsv;
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла " + e);
        }
    }
}
