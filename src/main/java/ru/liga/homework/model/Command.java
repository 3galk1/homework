package ru.liga.homework.model;

import lombok.Getter;
import ru.liga.homework.model.type.CurrencyConstants;
import ru.liga.homework.model.type.TimeRange;


@Getter
public class Command {
    private final TimeRange timeRange;
    private final String operation;
    private final CurrencyConstants currency;

    public Command(String operation,CurrencyConstants currency, TimeRange timeRange) {
        this.timeRange = timeRange;
        this.operation = operation;
        this.currency = currency;
    }
}
