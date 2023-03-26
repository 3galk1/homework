package ru.liga.forecastor.service;

import ru.liga.forecastor.model.Command;
import ru.liga.forecastor.model.CurrencyRate;
import ru.liga.forecastor.model.type.Currency;
import ru.liga.forecastor.model.type.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Parse {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();

    public Command parserFromConsole(String command) {
        String[] parsedCommand = command.split(" ");
        if (parsedCommand.length==3) {
            Currency currency = Currency.valueOf(parsedCommand[1].toUpperCase());
            Range timeRange = Range.valueOf(parsedCommand[2].toUpperCase());
            return new Command(parsedCommand[0] , currency , timeRange);
        }
        System.out.println("Ошибка при парсинге команды"); // временно, будет заменен на ошибку
        return null;
    }

    public List<CurrencyRate> parserFromCsv(List<String> dataFromCsv, Range rateRange) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (int i = 0; i < rateRange.getValue(); i++) {
            String[] parsedData = dataFromCsv.get(i).split(";");
            rates.add(new CurrencyRate(Integer.parseInt(parsedData[0].replace("\"" , "")) ,
                    LocalDate.parse(parsedData[1].replace("\"" , "") , formatter) ,
                    BigDecimal.valueOf(Double.parseDouble(parsedData[2].replace("\"" , "").replace("," , "."))) ,
                    parsedData[3].replace("\"" , "")));
        }
        return rates;
    }
}
