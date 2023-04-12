package ru.liga.forecaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.liga.forecaster.model.type.*;

import java.time.LocalDate;
import java.util.List;
@Setter
@AllArgsConstructor
@Getter
public class Command {
    private final Operation operation;
    private final List<Currency> currency;
    private final Range timeRange;
    private final AlgorithmType algorithm;
    private final Output output;
    private final LocalDate date;

    private Command(CommandBuilder commandBuilder) {
        operation = commandBuilder.operation;
        currency = commandBuilder.currency;
        timeRange = commandBuilder.timeRange;
        algorithm = commandBuilder.algorithm;
        output = commandBuilder.output;
        date = commandBuilder.date;
    }

    @Getter
    @Setter
    public static class CommandBuilder {
        private  Operation operation;
        private  List<Currency> currency;
        private  Range timeRange;
        private  AlgorithmType algorithm;
        private  Output output;
        private  LocalDate date;

        public CommandBuilder operation (Operation operation){
            this.operation = operation;
            return this;
        }
        public CommandBuilder currency (List<Currency> currency){
            this.currency = currency;
            return this;
        }
        public CommandBuilder timeRange (Range timeRange){
            this.timeRange = timeRange;
            return this;
        }
        public CommandBuilder algorithm (AlgorithmType algorithm){
            this.algorithm = algorithm;
            return this;
        }
        public CommandBuilder output (Output output){
            this.output = output;
            return this;
        }
        public CommandBuilder date (LocalDate date){
            this.date = date;
            return this;
        }


        public Command build() {
            return new Command(this);
        }

    }
}
