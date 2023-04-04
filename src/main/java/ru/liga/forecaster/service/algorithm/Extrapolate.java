package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Extrapolate implements Algorithm {
    public List<CurrencyRate> extrapolate(List<CurrencyRate> rates, Command command) {
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
                BigDecimal sumCourse = BigDecimal.ZERO;
                String currency = "";
                for (int i = 0; i < Range.RATE_RANGE.getDays(); i++) {
                    CurrencyRate currentRate = rates.get(i);
                    currency = currentRate.getCurrency();
                    sumCourse = sumCourse.add(currentRate.getCourse());
                }
                rates.add(0, new CurrencyRate(
                        rates.get(0).getNominal(),
                        (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1),
                        sumCourse.divide(BigDecimal.valueOf(Range.RATE_RANGE.getDays()), 4, RoundingMode.HALF_UP),
                        currency));
        }
        return rates;
    }
}
