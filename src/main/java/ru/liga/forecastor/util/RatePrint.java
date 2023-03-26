package ru.liga.forecastor.util;

import ru.liga.forecastor.model.Command;
import ru.liga.forecastor.model.CurrencyRate;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RatePrint {
    public void ratePrint(List<CurrencyRate> data , Command command) {
        if (command.getTimeRange().getValue() == 1) {
            CurrencyRate rate = data.get(0);
            System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                    + rate.getCourse().toString().replace("." , ","));
        } else {
            for (int i = data.size() - 1; i >= 0; i--) {
                CurrencyRate rate = data.get(i);
                System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                        + rate.getCourse().toString().replace("." , ","));
            }
        }
    }

}

