package ru.liga.forecaster.repository;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Slf4j
public class CsvReader {
    private final static int FIRST_INDEX = 0;

    public static List<String> readCsv(String path) throws IOException {
        log.info("Начало чтения файла");
        List<String> dataFromCsv = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream(path);
        InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(input) , StandardCharsets.UTF_8);
        BufferedReader csvReader = new BufferedReader(streamReader);
        String row;
        while ((row = csvReader.readLine()) != null) {
            dataFromCsv.add(row);
        }
        dataFromCsv.remove(FIRST_INDEX);
        csvReader.close();
        log.info("Конец чтения файла");
        return dataFromCsv;
    }
}
