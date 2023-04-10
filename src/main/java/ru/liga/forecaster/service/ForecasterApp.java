package ru.liga.forecaster.service;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.repository.CsvReader;
import ru.liga.forecaster.service.algorithm.AlgorithmFactory;
import ru.liga.forecaster.util.CreateCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForecasterApp {

    public static List<List<CurrencyRate>> StartForecaster(Map<String, String> arguments) throws IOException {
        String[] curCount = arguments.get("currency").split(",");
        List<List<CurrencyRate>> extrapolated = new ArrayList<>();
        for (String cur : curCount) {
            arguments.replace("currency" , cur);
            Command command = new CreateCommand().CreateCommand(arguments);
            List<CurrencyRate> rates = Parser.ParseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath()));
            extrapolated.add(new Forecaster(new AlgorithmFactory()).ForecastRate(rates , command));
        }
        return extrapolated;
    }
}
