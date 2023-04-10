package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mystical implements ForecastAlgorithm {
    private final List<CurrencyRate> mysticalRates = new ArrayList<>();
    private final LocalDate endDate = LocalDate.of(2005 , 01 , 01);

    public void ExtrapolatedOnTomorrow(List<CurrencyRate> rates , LocalDate startDate , boolean isFound) {
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

    public List<CurrencyRate> ExtrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            ExtrapolatedOnTomorrow(rates , command.getDate().plusDays(k) , false);
            if (mysticalRates.isEmpty()) {
                throw new RuntimeException("Ошибка данных, отсутсвуют записи для расчета");
            }
            double index = Math.random() * mysticalRates.size();
            WriteExtrapolatedRate(rates , mysticalRates , (int) index);
        }
        return rates;
    }

    private void WriteExtrapolatedRate(List<CurrencyRate> rates , List<CurrencyRate> mysticalRates , int index) {
        rates.add(0 , new CurrencyRate(
                mysticalRates.get(index).getNominal() ,
                CheckDate(rates) ,
                mysticalRates.get(index).getCourse() ,
                mysticalRates.get(index).getCurrency()));
    }

    private LocalDate CheckDate(List<CurrencyRate> rates) {
        int firstRate = 0;
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }
}
