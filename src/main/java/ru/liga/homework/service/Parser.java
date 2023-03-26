package ru.liga.homework.service;

import ru.liga.homework.model.Command;
import ru.liga.homework.model.CurrencyRate;
import ru.liga.homework.model.type.CurrencyConstants;
import ru.liga.homework.model.type.TimeRange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Parser {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();

    public Command parserFromConsole(String command) {
        if (new Validation().validationDataFromConsole(command)) {
            String[] parsedCommand = command.split(" ");
            CurrencyConstants currency = CurrencyConstants.valueOf(parsedCommand[1].toUpperCase());
            TimeRange timeRange = TimeRange.valueOf(parsedCommand[2].toUpperCase());
            return new Command(parsedCommand[0] , currency , timeRange);
        }
        System.out.println("Ошибка при парсинге команды"); // временно, будет заменен на ошибку
        return null;
    }

    public List<CurrencyRate> parserFromCsv(List<String> dataFromCsv) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
            String[] parsedData = dataFromCsv.get(i).split(";");
            rates.add(new CurrencyRate(Integer.parseInt(parsedData[0].replace("\"" , "")) ,
                    LocalDate.parse(parsedData[1].replace("\"" , "") , formatter) ,
                    BigDecimal.valueOf(Double.parseDouble(parsedData[2].replace("\"" , "").replace("," , "."))) ,
                    parsedData[3].replace("\"" , "")));
        }
        return rates;
    }
}
