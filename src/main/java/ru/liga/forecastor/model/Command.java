package ru.liga.forecastor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecastor.model.type.Currency;
import ru.liga.forecastor.model.type.Range;

@AllArgsConstructor
@Getter
public class Command {
    private final Range timeRange;
    private final String operation;
    private final Currency currency;

    public Command(String operation, Currency currency, Range timeRange) {
        this.timeRange = timeRange;
        this.operation = operation;
        this.currency = currency;
    }
}
