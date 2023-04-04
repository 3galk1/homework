package ru.liga.forecaster.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;
import ru.liga.forecaster.service.algorithm.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class Forecast {

    public List<CurrencyRate> calculateRate(List<CurrencyRate> rates , Command command ) {
        AlgorithmFactory algorithmFactory = new AlgorithmFactory();
        return algorithmFactory
                .createAlgorithm(command.getAlgorithm())
                .extrapolate(rates , command);
    }
}

