package ru.liga.forecaster.util;

import lombok.Data;
import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@Getter
@Data
public class CreateCommand {
    private Operation operation;
    private Currency currency;
    private AlgorithmType algorithm;
    private Range timeRange;
    private Output output;
    private LocalDate date;

    private final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();

    public Command CreateCommand(Map<String, String> arguments) {
        try {
            operation = Operation.valueOf(arguments.get("rate").toUpperCase());
            currency = Currency.valueOf(arguments.get("currency").toUpperCase());
            algorithm = AlgorithmType.valueOf(arguments.get("alg").toUpperCase());
            if (arguments.containsKey("period")) {
                timeRange = Range.valueOf(arguments.get("period").toUpperCase());
                output = Output.valueOf(arguments.get("output").toUpperCase());
                date = LocalDate.now();
            }
            if (arguments.containsKey("date")) {
                date = LocalDate.parse(arguments.get("date") , formatter);
                timeRange = Range.TOMORROW;
            }
            return new Command(timeRange , operation , currency , algorithm , output , date);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
