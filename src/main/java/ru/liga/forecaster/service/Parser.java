package ru.liga.forecaster.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@AllArgsConstructor
@Getter
public class Parser {
    private static Operation operation;
    private static Currency currency;
    private static AlgorithmType algorithm;
    private static Range timeRange;
    private static Output output;

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();

    private static final Map<String, String> arguments = new HashMap<>();

    public static Map<String, String> parseNamedArgument(String[] argument) {
        boolean isParsed=false;
        for (String s : argument) {
            String[] namedArg = s.split(" ");
            switch (namedArg[0]) {
                case "period":
                case "alg":
                case "date":
                case "output":
                    if (namedArg[1].isEmpty()) {
                        throw new RuntimeException("Некорректная команда");
                    }
                    arguments.put(namedArg[0] , namedArg[1]);
                    isParsed = true;
            }
        }
        if(!isParsed){
            throw new RuntimeException("Некорректная команда");
        }
        return arguments;
    }

    public static Command parseFromTelegramBot(String message) throws RuntimeException {
        String[] parsedCommand = message.split("-");
        LocalDate date = LocalDate.now();
        try {
            parseNamedArgument(parsedCommand);
            String [] command = parsedCommand[0].split(" ");
            operation = Operation.valueOf(command[0].toUpperCase());
            currency = Currency.valueOf(command[1].toUpperCase());
            algorithm = AlgorithmType.valueOf(arguments.get("alg").toUpperCase());
            if (arguments.containsKey("period")) {
                timeRange = Range.valueOf(arguments.get("period").toUpperCase());
                output = Output.valueOf(arguments.get("output").toUpperCase());
            }
            if (arguments.containsKey("date")) {
                date = LocalDate.parse(arguments.get("date") , formatter);
                timeRange = Range.TOMORROW;
            }
            return new Command(operation , currency , timeRange , algorithm , output , date);
        } catch (RuntimeException e) {
            throw new RuntimeException("Некорректная команда",e);
        }
    }

    public static List<CurrencyRate> parseFromCsv(List<String> dataFromCsv ) {
        List<CurrencyRate> rates = new ArrayList<>();
        for (int i = 0; i < dataFromCsv.size(); i++) {
            String[] parsedData = dataFromCsv.get(i).split(";");
            rates.add(new CurrencyRate(Integer.parseInt(parsedData[0].replace("\"" , "")) ,
                    LocalDate.parse(parsedData[1].replace("\"" , "") , formatter) ,
                    BigDecimal.valueOf(Double.parseDouble(parsedData[2].replace("\"" , "").replace("," , "."))) ,
                    parsedData[3].replace("\"" , "")));
        }
        return rates;
    }

//    public static Command parseFromConsole(String command) throws RuntimeException {
//        String[] parsedCommand = command.split(" ");
//        if (parsedCommand.length == 3) {
//            Operation operation = Operation.valueOf(parsedCommand[0].toUpperCase());
//            Currency currency = Currency.valueOf(parsedCommand[1].toUpperCase());
//            Range timeRange = Range.valueOf(parsedCommand[2].toUpperCase());
//            return new Command(operation , currency , timeRange , null , null , LocalDate.now());
//        }
//        throw new RuntimeException("Некорректная команда");
//    }
}
