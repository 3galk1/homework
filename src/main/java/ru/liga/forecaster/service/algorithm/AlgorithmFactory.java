package ru.liga.forecaster.service.algorithm;

import lombok.AllArgsConstructor;
import ru.liga.forecaster.model.type.AlgorithmType;

@AllArgsConstructor
public class AlgorithmFactory {

    public Algorithm createAlgorithm(AlgorithmType type) {
        switch (type) {
            case EXTRA:
                return new Extrapolate();
            case LAST:
                return new LastYear();
            case LINEAR:
                return new LinearRegression();
            case MYST:
                return  new Mystical();
            default: throw new RuntimeException("Алгоритм не найден");
        }
    }
}
