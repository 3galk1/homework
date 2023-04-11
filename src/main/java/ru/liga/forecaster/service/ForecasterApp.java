package ru.liga.forecaster.service;

import lombok.Data;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.mapper.CommandMapper;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.repository.CsvReader;
import ru.liga.forecaster.service.algorithm.AlgorithmFactory;
import ru.liga.forecaster.service.parser.CsvParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class ForecasterApp {
    private ForecastService forecastService = new ForecastService();
    private AlgorithmFactory algorithmFactory = new AlgorithmFactory();
    private static String CURRENCY;

    public List<List<CurrencyRate>> StartForecaster(Command command) throws IOException, DataErrorException {
        List<List<CurrencyRate>> extrapolated = new ArrayList<>();
        for (Currency cur : command.getCurrency()) {
            List<CurrencyRate> rates = CsvParser.ParseFromCsv(CsvReader.readCsv(cur.getFilePath()));
            extrapolated.add(algorithmFactory.createAlgorithm(command.getAlgorithm())
                    .extrapolatedOnTimeRange(rates , command));
        }
        return extrapolated;
    }
}
