package ru.liga.forecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Command {
    private final Range timeRange;
    private final LocalDate date;
    private final Operation operation;
    private final Currency currency;
    private final AlgorithmType algorithm;
    private final Output output;

    public Command(Operation operation , Currency currency , Range timeRange , AlgorithmType algorithm , Output output , LocalDate date) {
        this.timeRange = timeRange;
        this.operation = operation;
        this.currency = currency;
        this.algorithm = algorithm;
        this.output = output;
        this.date = date;
    }

}
