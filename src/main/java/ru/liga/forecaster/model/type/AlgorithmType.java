package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlgorithmType {
    MYST("Mystical"),
    AVG("Average"),
    LINE("LinearRegression"),
    LAST("LastYear");

    private final String type;
}
