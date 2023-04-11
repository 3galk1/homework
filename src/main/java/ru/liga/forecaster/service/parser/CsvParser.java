package ru.liga.forecaster.service.parser;

import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class CsvParser {
    private static final int FIRST_INDEX = 0, SECOND_INDEX = 1, THIRD_INDEX = 2, FOURTH_INDEX = 3;
    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();


    public static List<CurrencyRate> ParseFromCsv(List<String> dataFromCsv) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (String s : dataFromCsv) {
            String[] parsedData = s.split(";");
            rates.add(
                    new CurrencyRate(
                            Integer.parseInt(parsedData[FIRST_INDEX].replace("\"" , "")) ,
                            LocalDate.parse(parsedData[SECOND_INDEX].replace("\"" , "") , formatter) ,
                            BigDecimal.valueOf(Double.parseDouble(parsedData[THIRD_INDEX].replace("\"" , "").replace("," , "."))) ,
                            parsedData[FOURTH_INDEX].replace("\"" , "")));
        }
        return rates;
    }
}
