package ru.liga.forecaster.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.repository.CsvReader;
import ru.liga.forecaster.service.parser.CsvParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class ForecasterApp {
    private ForecastService forecastService = new ForecastService();
    private static String CURRENCY;

    public List<List<CurrencyRate>> startForecaster(Command command) throws IOException, DataErrorException {
        List<List<CurrencyRate>> extrapolated = new ArrayList<>();
        log.info("Старт работы сервиса прогнозирования");
        for (Currency cur : command.getCurrency()) {
            List<CurrencyRate> rates = CsvParser.parseFromCsv(CsvReader.readCsv(cur.getFilePath()));
            extrapolated.add(forecastService.forecastRate(rates , command));
        }
        log.info("Завершение работы сервиса прогнозирования");
        return extrapolated;
    }
}
