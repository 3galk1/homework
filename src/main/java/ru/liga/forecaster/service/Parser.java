package ru.liga.forecaster.service;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.type.Operation;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Parser {

    private static final  DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();

    public static Command parseFromConsole(String command) throws RuntimeException{
        String[] parsedCommand = command.split(" ");
        if (parsedCommand.length==3) {
            Operation operation = Operation.valueOf(parsedCommand[0].toUpperCase());
            Currency currency = Currency.valueOf(parsedCommand[1].toUpperCase());
            Range timeRange = Range.valueOf(parsedCommand[2].toUpperCase());
            return new Command(operation, currency , timeRange);
        }
        throw new RuntimeException("Некорректная команда");
    }

    public static List<CurrencyRate> parseFromCsv(List<String> dataFromCsv, Range rateRange) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (int i = 0; i < rateRange.getDays(); i++) {
            String[] parsedData = dataFromCsv.get(i).split(";");
            rates.add(new CurrencyRate(Integer.parseInt(parsedData[0].replace("\"" , "")) ,
                    LocalDate.parse(parsedData[1].replace("\"" , "") , formatter) ,
                    BigDecimal.valueOf(Double.parseDouble(parsedData[2].replace("\"" , "").replace("," , "."))) ,
                    parsedData[3].replace("\"" , "")));
        }
        return rates;
    }
}
