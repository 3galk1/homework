package ru.liga.forecaster.service.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Mystical implements ForecastAlgorithm {
    private final List<CurrencyRate> mysticalRates = new ArrayList<>();
    private final LocalDate endDate = LocalDate.of(2005 , 1 , 1);
    private final LocalDate startDate = LocalDate.now();
    private static final String ERROR_MESSAGE = "Ошибка данных, отсутсвуют записи для расчета";
    private static final int FIRST_RATE = 0;

    public List<CurrencyRate> extrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) throws DataErrorException {
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            extrapolatedOnTomorrow(rates , startDate.plusDays(k));
            if (mysticalRates.isEmpty()) {
                throw new DataErrorException(ERROR_MESSAGE);
            }
            double index = Math.random() * mysticalRates.size();
            writeExtrapolatedRate(rates , mysticalRates , (int) index);
        }
        if (!command.getDate().equals(LocalDate.now())) {
            extrapolatedOnDate(rates , command);
        }
        return rates;
    }

    private void extrapolatedOnDate(List<CurrencyRate> rates , Command command) throws DataErrorException {
        LocalDate endDate = command.getDate();
        LocalDate currentDate = rates.get(FIRST_RATE).getDate();
        while (currentDate.isBefore(endDate)) {
            extrapolatedOnTimeRange(rates , command);
        }
    }

    private void extrapolatedOnTomorrow(List<CurrencyRate> rates , LocalDate startDate) {
        boolean isFound = false;
        for (CurrencyRate currentRate : rates) {
            LocalDate actualDate = startDate;
            while (!isFound) {
                if (currentRate.getDate().equals(actualDate = actualDate.minusYears(1))) {
                    mysticalRates.add(currentRate);
                    isFound = true;
                } else if (actualDate.isBefore(endDate)) {
                    break;
                }
            }
        }
    }

    private void writeExtrapolatedRate(List<CurrencyRate> rates , List<CurrencyRate> mysticalRates , int index) {
        rates.add(0 , new CurrencyRate.CurrencyRateBuilder()
                .nominal(mysticalRates.get(index).getNominal())
                .date(checkDate(rates))
                .course(mysticalRates.get(index).getCourse())
                .currency(mysticalRates.get(index).getCurrency())
                .build());
    }

    private LocalDate checkDate(List<CurrencyRate> rates) {
        int firstRate = 0;
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }
}
