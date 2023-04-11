package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.util.List;

public interface ForecastAlgorithm {
    List<CurrencyRate> extrapolatedOnTimeRange(List<CurrencyRate> rates, Command command) throws DataErrorException;
}
