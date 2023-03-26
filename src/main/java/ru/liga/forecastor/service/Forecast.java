package ru.liga.forecastor.service;

import ru.liga.forecastor.model.CurrencyRate;
import ru.liga.forecastor.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Forecast {

    private BigDecimal divisor = new BigDecimal(Range.RATE_RANGE.getValue());

    public List<CurrencyRate> calculateRate(List<CurrencyRate> rates , Range timeRange, Range rateRange) {
        {
            for (int k = 0; k < timeRange.getValue(); k++) {
                {
                    LocalDate todayDate = LocalDate.now();
                    BigDecimal sumCourse = BigDecimal.ZERO;
                    String currency = "";
                    for (int i = 0; i < rateRange.getValue(); i++) {
                        CurrencyRate currentRate = rates.get(i);
                        currency = currentRate.getCurrency();
                        sumCourse = sumCourse.add(convertToRealCourse(currentRate.getNominal() , currentRate.getCourse()));
                    }
                    todayDate = calculateDate(todayDate , timeRange , k);
                    addRateToList(rates , todayDate , sumCourse , currency);
                }
            }
            return rates;

        }
    }

    private LocalDate calculateDate(LocalDate temporaryDate , Range timeRange , int index) {
        if (timeRange.getValue() > 1) {
            temporaryDate = temporaryDate.plusDays(1 + index);
        } else {
            temporaryDate = temporaryDate.plusDays(1);
        }
        return temporaryDate;
    }

    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 2 , RoundingMode.HALF_UP);
    }

    private List<CurrencyRate> addRateToList(List<CurrencyRate> rates , LocalDate temporaryDate , BigDecimal sumCourse , String currency) {
        rates.add(0 , new CurrencyRate(
                1 , //converted to 1 nominal
                temporaryDate ,
                sumCourse.divide(divisor , 2 , RoundingMode.HALF_UP) ,
                currency));
        rates.remove(rates.size() - 1);
        return rates;
    }

}

