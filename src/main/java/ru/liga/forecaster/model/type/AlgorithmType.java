package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlgorithmType {
    MYST("Mystical"),
    EXTRA("Extrapolate"),
    LINEAR("LinearRegression"),
    LAST("LastYear");

    private final String type;
}
