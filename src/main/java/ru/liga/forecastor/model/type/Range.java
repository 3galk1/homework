package ru.liga.forecastor.model.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Range {
    TOMORROW(1),
    WEEK(7),
    RATE_RANGE(7);

    private int value;

}
