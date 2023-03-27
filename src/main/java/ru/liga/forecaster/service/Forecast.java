package ru.liga.forecaster.service;

import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Forecast {

    public static List<CurrencyRate> calculateRate(List<CurrencyRate> rates, Range timeRange, Range rateRange) {
        for (int k = 0; k < timeRange.getDays(); k++) {
            {
                BigDecimal sumCourse = BigDecimal.ZERO;
                String currency = "";
                for (int i = 0; i < rateRange.getDays(); i++) {
                    CurrencyRate currentRate = rates.get(i);
                    currency = currentRate.getCurrency();
                    sumCourse = sumCourse.add(currentRate.getCourse());
                }
                rates.add(0, new CurrencyRate(
                        rates.get(0).getNominal(),
                        (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1),
                        sumCourse.divide(BigDecimal.valueOf(rateRange.getDays()), 4, RoundingMode.HALF_UP),
                        currency));
            }
        }
        return rates;
    }
}
