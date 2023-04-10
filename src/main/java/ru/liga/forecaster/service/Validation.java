package ru.liga.forecaster.service;

import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.type.Operation;

public class Validation {

    public static boolean CurrencyValidation(String argument) {
//        if (argument.length() <=5 && argument.length()>0) {
            return argument.toUpperCase().contains(Currency.EUR.name())
                    || argument.toUpperCase().contains(Currency.USD.name())
                    || argument.toUpperCase().contains(Currency.TRY.name())
                    || argument.toUpperCase().contains(Currency.AMD.name())
                    || argument.toUpperCase().contains(Currency.BGN.name());
        }
//        else {
//            throw new RuntimeException("Некорректная команда");
//        }
//    }

    public static boolean OperationValidation(String argument) {
        return argument.toUpperCase().contains(Operation.RATE.name());
    }
}
