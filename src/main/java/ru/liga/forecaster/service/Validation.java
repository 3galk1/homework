package ru.liga.forecaster.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.type.NamedArgument;
import ru.liga.forecaster.model.type.Operation;

@Slf4j
@Data
public class Validation {
    private static final String
            ERROR_MESSAGE = "Некорректная команда: ",
            OVER_CURRENCY = "Превышено число валют в команде: ",
            NEED_ADD_CURRENCY = "Некорректная команда, добавьте валюту: ",
            NEED_ADD_RATE = "Некорректная команда, добавьте операцию: ",
            INCORRECT_CURRENCY = "Некорректный код валюты: ",
            INCORRECT_NAMED_ARGUMENT = "Некорректный именной аргумент: ";
    public static String errorMessage = "";

    public static String argumentsValidation(String message) {
        errorMessage = "";
        log.info("Старт валидации");
        if (currencyValidation(message)) {
            if (operationValidation(message)) {
                if (namedArgumentsValidation(message)) {
                    log.info("Завершение валидации");
                    return errorMessage;
                }
                ;
            }
        }
        return errorMessage;
    }

    private static boolean searchCurrency(String argument) {
        return argument.contains("EUR") || argument.contains("TRY")
                || argument.contains("USD") || argument.contains("AMD") || argument.contains("BGN");
    }

    private static boolean currencyValidation(String message) {
        String[] arguments = message.split(" ");
        for (String argument : arguments) {
            if (searchCurrency(argument)) {
                String[] curCount = argument.split(",");
                for (String cur : curCount) {
                    if (curCount.length > 5) {
                        log.error(OVER_CURRENCY);
                        errorMessage = errorMessage + "\n" + OVER_CURRENCY + message;
                        return false;
                    } else if (!Currency.findByName(cur)) {
                        log.error(INCORRECT_CURRENCY);
                        errorMessage = errorMessage + "\n" + INCORRECT_CURRENCY + cur + "\n" + message;
                        return false;
                    }
                }
                return true;
            }
        }
        log.error(NEED_ADD_CURRENCY);
        errorMessage = errorMessage + "\n" + NEED_ADD_CURRENCY + message;
        return false;
    }

    private static boolean operationValidation(String message) {
        if (!message.toUpperCase().contains(Operation.RATE.name())) {
            errorMessage = errorMessage + "\n" + NEED_ADD_RATE + message;
            return false;
        }
        return true;
    }

    private static boolean namedArgumentsValidation(String message) {
        String[] arguments = message.split("-");
        int countOfFindArgument = 0;
        for (String argument : arguments) {
            String[] namedArg = argument.split(" ");
            for (int i = 0; i < namedArg.length; i++) {
                if (NamedArgument.findByName(namedArg[i])) {
                    if (!namedArg[i + 1].isEmpty()) {
                        countOfFindArgument++;
                    }
                }
            }
        }
        if (countOfFindArgument == 3) {
            return true;
        }
        log.error(ERROR_MESSAGE);
        errorMessage = errorMessage + "\n" + ERROR_MESSAGE + message;
        return false;
    }
}
