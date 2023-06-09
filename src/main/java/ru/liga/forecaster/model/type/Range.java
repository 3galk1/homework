package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Range {
    TOMORROW(1),
    WEEK(7),
    MONTH(31),
    RATE_RANGE(7);

    private final int days;

}
