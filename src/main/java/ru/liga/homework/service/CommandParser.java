package ru.liga.homework.service;

import ru.liga.homework.model.CommandForUse;
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

public class CommandParser {

    public CommandForUse parserCommandFromConsole(String command) {
        switch (command.toLowerCase()) {
            case ("rate eur tomorrow"):
                return new CommandForUse(CurrencyConstants.EUR.getValue() , CurrencyConstants.EUR_PATH.getValue() , TimeRange.TOMORROW.getValue());
            case ("rate eur week"):
                return new CommandForUse(CurrencyConstants.EUR.getValue() , CurrencyConstants.EUR_PATH.getValue() , TimeRange.WEEK.getValue());
            case ("rate try tomorrow"):
                return new CommandForUse(CurrencyConstants.TRY.getValue() , CurrencyConstants.TRY_PATH.getValue() , TimeRange.TOMORROW.getValue());
            case ("rate try week"):
                return new CommandForUse(CurrencyConstants.TRY.getValue() , CurrencyConstants.TRY_PATH.getValue() , TimeRange.WEEK.getValue());
            case ("rate usd tomorrow"):
                return new CommandForUse(CurrencyConstants.USD.getValue() , CurrencyConstants.USD_PATH.getValue() , TimeRange.TOMORROW.getValue());
            case ("rate usd week"):
                return new CommandForUse(CurrencyConstants.USD.getValue() , CurrencyConstants.USD_PATH.getValue() , TimeRange.WEEK.getValue());
            default:
                System.out.println("Неверная команда " + command);
                return null;
        }
    }

    public List<CurrencyRate> parserDatafromCsv (List<String> dataFromCsv) {
        List<CurrencyRate> rates = new ArrayList<>();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();
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
