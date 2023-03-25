package ru.liga.homework.service;

import ru.liga.homework.model.CommandForUse;
import ru.liga.homework.model.CurrencyRate;
import ru.liga.homework.model.type.TimeRange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class RateForecastService {

    private BigDecimal divisor = new BigDecimal(7);

    public List<CurrencyRate> calculateRate(List<CurrencyRate> rates , CommandForUse command) {
        if (command.getTimeRange() == 1) {
            return calculateRateOnTomorrow(rates , command , 1);
        } else {
            return calculateRateOnWeek(rates , command);
        }
    }

    public List<CurrencyRate> calculateRateOnTomorrow(List<CurrencyRate> rates , CommandForUse command , int index) {
        LocalDate temporaryDate = LocalDate.now();
        BigDecimal sumCourse = new BigDecimal(0);
        for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
            CurrencyRate currentRate = rates.get(i);
            sumCourse = sumCourse.add(currentRate.getCourse());
        }
        if (command.getTimeRange() == 7) {
            temporaryDate=temporaryDate.plusDays(1+index);
        } else {
            temporaryDate=temporaryDate.plusDays(1);
        }
        rates.add(0 , new CurrencyRate(
                1 ,
                temporaryDate ,
                sumCourse.divide(divisor , 2 , RoundingMode.HALF_UP) ,
                command.getType()));
        rates.remove(rates.size() - 1);
        return rates;
    }

    public List<CurrencyRate> calculateRateOnWeek(List<CurrencyRate> rates , CommandForUse command) {
        for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
            rates = calculateRateOnTomorrow(rates , command , i);
        }
        return rates;
    }
}

