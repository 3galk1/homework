package ru.liga.forecaster.mapper;

import lombok.Data;
import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@Getter
@Data
public class CommandMapper {
    private final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();
    private Range timeRange;
    private LocalDate date;
    private List<Currency> currency = new ArrayList<>();

    public Command MapCommand(Map<String, String> arguments) {
        String[] cur = arguments.get("currency").split(",");
        List<Currency> currency = new ArrayList<>();
        for (String currentCur : cur) {
            currency.add(Currency.valueOf(currentCur));
        }

        if (arguments.containsKey("period")) {
            timeRange = Range.valueOf(arguments.get("period").toUpperCase());
            date = LocalDate.now();
        } else if (arguments.containsKey("date")) {
            date = LocalDate.parse(arguments.get("date") , formatter);
            timeRange = Range.TOMORROW;
        }

        return new Command(Operation.valueOf(arguments.get("rate").toUpperCase()) ,
                currency ,
                timeRange ,
                AlgorithmType.valueOf(arguments.get("alg").toUpperCase()) ,
                Output.valueOf(arguments.get("output").toUpperCase()) ,
                date);
    }
}
