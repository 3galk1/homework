package ru.liga.forecaster.service.parser;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.type.NamedArgument;
import ru.liga.forecaster.service.Validation;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class Parser {
    private static Map<String, String> parsedArguments = new HashMap<>();
    private static final int NAME = 0, VALUE = 1;
    private static final String CURRENCY = "currency", RATE = "rate", ERROR = "error";

    public static Map<String, String> parseArguments(String originalMessage) {

        if (Validation.argumentsValidation(originalMessage).isEmpty()) {
            log.info("Старт парсинга");
            parseCurrency(originalMessage);
            parseRate(originalMessage);
            parseNamedArguments(originalMessage);
            log.info("Завершение парсинга");
            return parsedArguments;
        }
        parsedArguments.put(ERROR , Validation.errorMessage);
        return parsedArguments;
    }

    private static void parseCurrency(String message) {
        String[] arguments = message.split(" ");
        for (String argument : arguments) {
            String[] curCount = argument.split(",");
            for (String cur : curCount) {
                if (Currency.findByName(cur)) {
                    parsedArguments.put(CURRENCY , argument);
                    break;
                }
            }
        }
    }

    private static void parseRate(String message) {
        String[] arguments = message.split(" ");
        for (String argument : arguments) {
            if (argument.contains(RATE)) {
                parsedArguments.put(RATE , argument);
            }
        }
    }

    private static void parseNamedArguments(String message) {
        String[] arguments = message.split("-");
        for (String argument : arguments) {
            String[] namedArgument = argument.split(" ");
            for (int i = 0; i < namedArgument.length; i++) {
                if (NamedArgument.findByName(namedArgument[i])) {
                    parsedArguments.put(namedArgument[i] , namedArgument[i + 1]);
                }
            }
        }
    }
}
