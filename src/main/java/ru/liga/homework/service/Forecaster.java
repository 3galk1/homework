package ru.liga.homework.service;

import ru.liga.homework.model.Command;
import ru.liga.homework.model.CurrencyRate;
import ru.liga.homework.model.type.TimeRange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Forecaster {

    private BigDecimal divisor = new BigDecimal(7);

    public List<CurrencyRate> calculateRate(List<CurrencyRate> rates , Command command) {
        if (command.getTimeRange().getValue() == 1) {
            return calculateRateOnTomorrow(rates , command , 1);
        } else {
            return calculateRateOnWeek(rates , command);
        }
    }

    private LocalDate calculateDate(LocalDate temporaryDate, Command command , int index) {
        if (command.getTimeRange().getValue() == 7) {
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

    public List<CurrencyRate> calculateRateOnTomorrow(List<CurrencyRate> rates , Command command , int index) {
        LocalDate temporaryDate = LocalDate.now();
        BigDecimal sumCourse = new BigDecimal(0);
        for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
            CurrencyRate currentRate = rates.get(i);
            sumCourse = sumCourse.add(convertToRealCourse(currentRate.getNominal() , currentRate.getCourse()));
        }
        temporaryDate = calculateDate(temporaryDate,command , index);
        return addRateToList(rates , temporaryDate , sumCourse , command.getCurrency().getName());
    }

    public List<CurrencyRate> calculateRateOnWeek(List<CurrencyRate> rates , Command command) {
        for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
            rates = calculateRateOnTomorrow(rates , command , i);
        }
        return rates;
    }
}

