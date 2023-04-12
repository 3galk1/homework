package ru.liga.forecaster.mapper;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Getter
@Data
public class CommandMapper {
    private final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();
    private Range timeRange;
    private LocalDate date;
    private List<Currency> currency = new ArrayList<>();

    public Command MapCommand(Map<String, String> arguments) {
        log.info("Маппинг входящих аргументов");
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
        log.info("Завершение маппинга");
        return new Command.CommandBuilder()
                .operation(Operation.valueOf(arguments.get("rate").toUpperCase()))
                .currency(currency)
                .timeRange(timeRange)
                .algorithm(AlgorithmType.valueOf(arguments.get("alg").toUpperCase()))
                .output(Output.valueOf(arguments.get("output").toUpperCase()))
                .date(date)
                .build();
    }
}
