package ru.liga.forecaster.service.algorithm;

import lombok.Data;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.time.LocalDate;
import java.util.List;

@Data
public class LastYear implements ForecastAlgorithm {
    private static final int FIRST_RATE = 0;
    private static final String ERROR_MESSAGE = "Ошибка данных, отсутсвуют записи для расчета";
    private LocalDate startDate = LocalDate.now().minusYears(1);

    public List<CurrencyRate> extrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) throws DataErrorException {
        if (!command.getDate().equals(LocalDate.now())) {
            extrapolatedOnDate(rates , command);
        } else {
            for (int k = 0; k < command.getTimeRange().getDays(); k++) {
                extrapolatedOnTomorrow(rates , startDate.plusDays(k));
            }
        }
        return rates;
    }

    private void extrapolatedOnDate(List<CurrencyRate> rates , Command command) throws DataErrorException {
        LocalDate endDate = command.getDate();
        LocalDate currentDate = LocalDate.now();
        for (int k = 0; currentDate.isBefore(endDate); k++) {
            extrapolatedOnTomorrow(rates , startDate.plusDays(k));
            currentDate = rates.get(FIRST_RATE).getDate();
        }
    }

    private void extrapolatedOnTomorrow(List<CurrencyRate> rates , LocalDate startDate) throws DataErrorException {
        for (CurrencyRate currentRate : rates) {
            LocalDate currentDay = startDate;
            for (int i = 0; i < Range.WEEK.getDays(); i++) {
                if (currentRate.getDate().equals(currentDay = currentDay.minusDays(i))) {
                    writeExtrapolatedRate(currentRate , rates);
                    return;
                }
            }
        }
        throw new DataErrorException(ERROR_MESSAGE);
    }

    private LocalDate checkDate(List<CurrencyRate> rates) {
        int firstRate = 0;
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }

    private void writeExtrapolatedRate(CurrencyRate currentRate , List<CurrencyRate> rates) {
        int firstRate = 0;
        rates.add(firstRate , new CurrencyRate.CurrencyRateBuilder()
                .nominal(currentRate.getNominal())
                .date(checkDate(rates))
                .course(currentRate.getCourse())
                .currency(currentRate.getCurrency())
                .build());
    }
}
