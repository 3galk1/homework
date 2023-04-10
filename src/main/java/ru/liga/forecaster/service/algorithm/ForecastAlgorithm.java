package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.util.List;

public interface ForecastAlgorithm {
    List<CurrencyRate> ExtrapolatedOnTimeRange(List<CurrencyRate> rates, Command command);
}
