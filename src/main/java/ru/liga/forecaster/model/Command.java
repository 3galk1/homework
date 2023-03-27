package ru.liga.forecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.type.Operation;
import ru.liga.forecaster.model.type.Range;

@AllArgsConstructor
@Getter
public class Command {
    private final Range timeRange;
    private final Operation operation;
    private final Currency currency;

    public Command(Operation operation, Currency currency, Range timeRange) {
        this.timeRange = timeRange;
        this.operation = operation;
        this.currency = currency;
    }
}
