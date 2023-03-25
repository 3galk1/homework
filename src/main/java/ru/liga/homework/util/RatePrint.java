package ru.liga.homework.util;

import ru.liga.homework.model.CurrencyRate;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RatePrint {
    public void ratePrintOnWeek(List<CurrencyRate> data) {
        for (int i = data.size()-1; i >= 0; i--) {
            CurrencyRate rate = data.get(i);
            System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                    + rate.getCourse().toString().replace(".",","));
        }
    }

    public void ratePrintOnTomorrow(List<CurrencyRate> data) {
        CurrencyRate rate = data.get(0);
        System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                + rate.getCourse().toString().replace(".",","));
    }
}
