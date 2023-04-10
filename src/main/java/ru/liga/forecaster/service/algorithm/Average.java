package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Average implements ForecastAlgorithm {
    private final int firstRate = 0;

    private void ExtrapolatedOnTomorrow(List<CurrencyRate> rates , Command command) {
        BigDecimal sumCourse = BigDecimal.ZERO;
        for (int i = 0; i < Range.RATE_RANGE.getDays(); i++) {
            CurrencyRate currentRate = rates.get(i);
            sumCourse = sumCourse.add(currentRate.getCourse());
        }
        WriteExtrapolatedRate(sumCourse , rates , command);
    }

    public List<CurrencyRate> ExtrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            ExtrapolatedOnTomorrow(rates , command);
        }
        return rates;
    }


    private void WriteExtrapolatedRate(BigDecimal sumCourse , List<CurrencyRate> rates , Command command) {
        rates.add(0 , new CurrencyRate(
                rates.get(firstRate).getNominal() ,
                CheckDate(rates) ,
                sumCourse.divide(BigDecimal.valueOf(Range.RATE_RANGE.getDays()) , 4 , RoundingMode.HALF_UP) ,
                command.getCurrency().toString()));
    }

    private LocalDate CheckDate(List<CurrencyRate> rates) {
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }
}
