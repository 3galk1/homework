package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.time.LocalDate;
import java.util.List;

public class LastYear implements ForecastAlgorithm {

    private void ExtrapolatedOnTomorrow(List<CurrencyRate> rates , LocalDate endDate) {
        for (CurrencyRate currentRate : rates) {
            LocalDate currentDay = endDate;
            for (int i = 0; i < Range.WEEK.getDays(); i++) {
                if (currentRate.getDate().equals(currentDay = currentDay.minusDays(i))) {
                    WriteExtrapolatedRate(currentRate , rates);
                    return;
                }
            }
        }
        throw new RuntimeException("Ошибка данных, отсутсвуют записи для расчета");
    }

    public List<CurrencyRate> ExtrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        LocalDate endDate = command.getDate().minusYears(1);
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            endDate = endDate.plusDays(k);
            ExtrapolatedOnTomorrow(rates , endDate);
        }
        return rates;
    }

    private LocalDate CheckDate(List<CurrencyRate> rates) {
        int firstRate = 0;
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }

    private void WriteExtrapolatedRate(CurrencyRate currentRate , List<CurrencyRate> rates) {
        int firstRate = 0;
        rates.add(firstRate , new CurrencyRate(
                currentRate.getNominal() ,
                CheckDate(rates) ,
                currentRate.getCourse() ,
                currentRate.getCurrency()));
    }
}
