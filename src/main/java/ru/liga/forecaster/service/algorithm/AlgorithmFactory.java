package ru.liga.forecaster.service.algorithm;

import lombok.AllArgsConstructor;
import ru.liga.forecaster.model.type.AlgorithmType;

@AllArgsConstructor
public class AlgorithmFactory {

    public ForecastAlgorithm createAlgorithm(AlgorithmType type) {
        switch (type) {
            case AVG:
                return new Average();
            case LAST:
                return new LastYear();
            case LINE:
                return new LinearRegression();
            case MYST:
                return new Mystical();
            default:
                throw new RuntimeException("Алгоритм не найден");
        }
    }
}
