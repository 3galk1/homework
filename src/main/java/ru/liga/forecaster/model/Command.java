package ru.liga.forecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class Command {
    private final Operation operation;
    private final List<Currency> currency;
    private final Range timeRange;
    private final AlgorithmType algorithm;
    private final Output output;
    private final LocalDate date;

}
