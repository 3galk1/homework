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
    private final static int FIRST_RATE = 0;
    private final static String ERROR_MESSAGE = "Ошибка данных, отсутсвуют записи для расчета";
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
                    WriteExtrapolatedRate(currentRate , rates);
                    return;
                }
            }
        }
        throw new DataErrorException(ERROR_MESSAGE);
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
