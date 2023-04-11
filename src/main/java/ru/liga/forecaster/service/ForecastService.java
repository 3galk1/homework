package ru.liga.forecaster.service;

import lombok.Data;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.service.algorithm.AlgorithmFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class ForecastService {
    AlgorithmFactory algorithmFactory;

    public List<CurrencyRate> forecastRate(List<CurrencyRate> rates , Command command) throws DataErrorException {
        return getExtrapolatedRate(algorithmFactory
                .createAlgorithm(command.getAlgorithm())
                .extrapolatedOnTimeRange(rates , command) , command);
    }

    private List<CurrencyRate> getExtrapolatedRate(List<CurrencyRate> rates , Command command) {
        List<CurrencyRate> extrapolatedRate = new ArrayList<>();
        for (int i = 0; i < command.getTimeRange().getDays(); i++) {
            extrapolatedRate.add(i , rates.get(i));
        }
        return extrapolatedRate;
    }
}

