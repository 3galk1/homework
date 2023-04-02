package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mystical {

    public List<CurrencyRate> calculateMysticalRate(List<CurrencyRate> rates , Command command) {
        for (int k = 0; k < command.getTimeRange().getDays(); k++) {
            List<CurrencyRate> mysticalRates = new ArrayList<>();
            LocalDate inDate = LocalDate.now().plusDays(k); //Дата с command
            for (CurrencyRate currentRate : rates) {
                boolean isFound = false;
                LocalDate actualDate = inDate;
                while (!isFound) {
                    if (currentRate.getDate().equals(actualDate=actualDate.minusYears(1))) {
                        mysticalRates.add(currentRate);
                        isFound = true;
                    } else if (actualDate.isBefore(LocalDate.of(2005 , 01 , 01))) {
                        break;
                    }
                }
            }
            if (mysticalRates.isEmpty()) {
                throw new RuntimeException("Ошибка данных, отсутсвуют записи для расчета");
            }
            double index = Math.random() * mysticalRates.size(); //рандомный элемент из массива
            rates.add(0 , new CurrencyRate(
                    mysticalRates.get((int)index).getNominal() ,
                    (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1) ,
                    mysticalRates.get((int)index).getCourse() ,
                    mysticalRates.get((int)index).getCurrency()));

        }
        return rates;
    }
}
