package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Average implements ForecastAlgorithm {
    private final static int FIRST_RATE = 0;

    public List<CurrencyRate> extrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        if (!command.getDate().equals(LocalDate.now())) {
            extrapolatedOnDate(rates , command);
        } else {
            int timeRange = command.getTimeRange().getDays();
            for (int day = 0; day < timeRange; day++) {
                extrapolatedOnTomorrow(rates , command);
            }
        }
        return rates;
    }

    private void extrapolatedOnDate(List<CurrencyRate> rates , Command command) {
        LocalDate endDate = command.getDate();
        LocalDate currentDate=LocalDate.now();
        while (currentDate.isBefore(endDate)){
            extrapolatedOnTomorrow(rates,command);
            currentDate = rates.get(FIRST_RATE).getDate();
        }
    }

    private void extrapolatedOnTomorrow(List<CurrencyRate> rates , Command command) {
        BigDecimal sumCourse = BigDecimal.ZERO;
        for (int rateIndex = 0; rateIndex < Range.RATE_RANGE.getDays(); rateIndex++) {
            CurrencyRate currentRate = rates.get(rateIndex);
            sumCourse = sumCourse.add(currentRate.getCourse());
        }
        writeExtrapolatedRate(sumCourse , rates , command);
    }

    private void writeExtrapolatedRate(BigDecimal sumCourse , List<CurrencyRate> rates , Command command) {
        rates.add(0 , new CurrencyRate(
                rates.get(FIRST_RATE).getNominal() ,
                checkDate(rates) ,
                sumCourse.divide(BigDecimal.valueOf(Range.RATE_RANGE.getDays()) , 4 , RoundingMode.HALF_UP) ,
                command.getCurrency().toString()));
    }

    private LocalDate checkDate(List<CurrencyRate> rates) {
        return (rates.get(FIRST_RATE).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(FIRST_RATE).getDate()).plusDays(1);
    }
}
