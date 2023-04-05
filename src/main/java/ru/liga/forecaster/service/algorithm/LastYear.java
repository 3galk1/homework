package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.time.LocalDate;
import java.util.List;

public class LastYear implements Algorithm{

    public List<CurrencyRate> extrapolate(List<CurrencyRate> rates , Command command) {
        LocalDate inDate = command.getDate().minusYears(1);
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            inDate = inDate.plusDays(k);
            boolean isFound = false;
            for (CurrencyRate currentRate : rates) {
                LocalDate actualDate = inDate;
                for (int i = 0; i < Range.WEEK.getDays(); i++) {
                    if (currentRate.getDate().equals(actualDate = actualDate.minusDays(i))) {
                        rates.add(0 , new CurrencyRate(
                                currentRate.getNominal() ,
                                (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1) ,
                                currentRate.getCourse() ,
                                currentRate.getCurrency()));
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    break;
                }
            }
            if (!isFound) {
                throw new RuntimeException("Ошибка данных, отсутсвуют записи для расчета");
            }
        }
        return rates;
    }
}
