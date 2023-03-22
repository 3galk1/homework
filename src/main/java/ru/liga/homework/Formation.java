package ru.liga.homework;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Formation {
    public void formateDataOnWeek(List<CurrencyRate> data) {
        for (int i = 0; i < 7; i++) {
            CurrencyRate rate = data.get(i);
            System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                    + rate.getCourse().toString().replace(".",","));
        }
    }

    public void formateDataOnTomorrow(CurrencyRate data) {

        CurrencyRate rate = data;
        System.out.println(DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate()) + " - "
                + rate.getCourse().toString().replace(".",","));
    }
}
