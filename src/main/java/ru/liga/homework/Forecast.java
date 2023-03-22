package ru.liga.homework;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Forecast {

    public CurrencyRate calculateRateOnTommorow(List<CurrencyRate> rate) {
        BigDecimal devisor = new BigDecimal(7);
        BigDecimal courseTomorrow = new BigDecimal(0);
        String currency = "";
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            CurrencyRate currencyRate = rate.get(i);
            courseTomorrow  = courseTomorrow .add(currencyRate.getCourse());
            currency = currencyRate.getCurrency();
        }
        CurrencyRate rateTomorrow = new CurrencyRate(
                1 ,
                date.plusDays(1) ,
                courseTomorrow = courseTomorrow .divide(devisor , 2 , BigDecimal.ROUND_HALF_UP) ,
                currency);
        return rateTomorrow;
    }

    public List<CurrencyRate> calculateRateOnWeek(List<CurrencyRate> rate) {
        for (int i = 0; i < 7; i++) {
            CurrencyRate currentRate = calculateRateOnTommorow(rate);
            LocalDate temporaryDate = currentRate.getDate();
            currentRate.setDate(temporaryDate.plusDays(i));
            rate.add(0 , currentRate);
            rate.remove(rate.size() - 1);
        }
        return rate;
    }

}


