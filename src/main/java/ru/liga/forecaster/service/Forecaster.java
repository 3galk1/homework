package ru.liga.forecaster.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.service.algorithm.AlgorithmFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Forecaster {

    AlgorithmFactory algorithmFactory;

    public List<CurrencyRate> ForecastRate(List<CurrencyRate> rates , Command command) {
        return GetExtrapolatedRate(algorithmFactory
                .createAlgorithm(command.getAlgorithm())
                .ExtrapolatedOnTimeRange(rates , command),command);
    }

    private List<CurrencyRate> GetExtrapolatedRate(List<CurrencyRate> rates , Command command) {
        List<CurrencyRate> extrapolatedRate = new ArrayList<>();
        for (int i=0;i<command.getTimeRange().getDays();i++){
            extrapolatedRate.add(i, rates.get(i));
        }
        return extrapolatedRate;
    }
}

