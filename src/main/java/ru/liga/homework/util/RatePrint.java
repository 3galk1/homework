package ru.liga.homework.util;

import ru.liga.homework.model.Command;
import ru.liga.homework.model.CurrencyRate;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RatePrint {
    public void ratePrint(List<CurrencyRate> data , Command command) {
        if (command.getTimeRange().getValue() == 1) {
            CurrencyRate rate = data.get(0);
            System.out.println("Курс валюты на завтра:" + "\n"
                    + DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                    + rate.getCourse().toString().replace("." , ","));
        } else {
            System.out.println("Курс валюты на неделю:");
            for (int i = data.size() - 1; i >= 0; i--) {
                CurrencyRate rate = data.get(i);
                System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                        + rate.getCourse().toString().replace("." , ","));
            }
        }
    }

}

