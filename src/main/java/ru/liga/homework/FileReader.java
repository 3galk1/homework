package ru.liga.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class FileReader {

    private boolean isFileExists(String path) {
        File file = new File(path);
        return file.isFile();
    }

    private String getPath(String currency) {
        String path = "";
        switch (currency.toUpperCase()) {
            case "EUR":
                path = "src/main/resources/eur.csv";
                break;
            case "USD":
                path = "src/main/resources/try.csv";
                break;
            case "TRY":
                path = "src/main/resources/usd.csv";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currency);
        }
        return path;
    }

    public List<CurrencyRate> getDataFromCsvFile(String currency) throws IOException, ParseException {
        if (isFileExists(getPath(currency))) {
            List<CurrencyRate> rate = new LinkedList<>();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();
            BufferedReader csvReader = new BufferedReader(new java.io.FileReader(getPath(currency)));
            String row = csvReader.readLine(); //чтение первой строки с Headers
            for (int i = 0; i < 7; i++) {
                    String[] line = csvReader.readLine().split(";");
                    rate.add(new CurrencyRate(Integer.parseInt(line[0].replace("\"","")) ,
                            LocalDate.parse(line[1].replace("\"","") , formatter) ,
                            BigDecimal.valueOf(Double.parseDouble(line[2].replace("\"","").replace("," , "."))) ,
                            line[3].replace("\"","")));
            }
            return rate;
        }
        else {throw new RuntimeException("File is not Exists");}
    }
}
