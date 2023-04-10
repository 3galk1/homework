package ru.liga.forecaster.service;

import lombok.Data;
import lombok.Getter;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@Getter
@Data
public class Parser {
    private final static int firstIndex = 0, secondIndex = 1, thirdIndex = 2, fourthIndex = 3;
    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();
    private static final Map<String, String> arguments = new HashMap<>();
    private static final int name = 0, value = 1;

    public static Map<String, String> ParseMessage(String originalMessage) {
        String[] args = originalMessage.split("-");
        try {
            if (!ParseNamedArguments(args)) {
                throw new RuntimeException("Некорректная команда");
            }
            ParseOtherArguments(args);
        } catch (RuntimeException e) {
            throw e;
        }
        return Parser.arguments;
    }

    private static void ParseOtherArguments(String[] args) {
        try {
            for (String arg : args) {
                if (Validation.CurrencyValidation(arg)) {
                    String[] currencyValue = arg.split(" ");
                    arguments.put("currency" , currencyValue[value]);
                }
                if (Validation.OperationValidation(arg)) {
                    arguments.put("rate" , "rate");
                }
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private static boolean ParseNamedArguments(String[] args) {
        boolean isParsed = false;
        for (String s : args) {
            String[] namedArgument = s.split(" ");
            switch (namedArgument[name]) {
                case "period":
                case "alg":
                case "date":
                case "output":
                    if (namedArgument[value].isEmpty()) {
                        throw new RuntimeException("Некорректная команда");
                    }
                    Parser.arguments.put(
                            namedArgument[name] ,
                            namedArgument[value].toUpperCase());
                    isParsed = true;
            }
        }
        return isParsed;
    }

    public static List<CurrencyRate> ParseFromCsv(List<String> dataFromCsv) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (String s : dataFromCsv) {
            String[] parsedData = s.split(";");
            rates.add(
                    new CurrencyRate(
                            Integer.parseInt(parsedData[firstIndex].replace("\"" , "")) ,
                            LocalDate.parse(parsedData[secondIndex].replace("\"" , "") , formatter) ,
                            BigDecimal.valueOf(Double.parseDouble(parsedData[thirdIndex].replace("\"" , "").replace("," , "."))) ,
                            parsedData[fourthIndex].replace("\"" , "")));
        }
        return rates;
    }
}
