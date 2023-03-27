package ru.liga.forecaster.service;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;
import ru.liga.forecaster.util.reader.CommandReader;
import ru.liga.forecaster.util.RatePrint;
import ru.liga.forecaster.util.reader.CsvReader;

import java.util.List;

public class CurrencyRateApp {
    public static void StartForecaster() {
        Command command = Parser.parseFromConsole(CommandReader.readCommand());
        List<CurrencyRate> rates = Parser.parseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath(), Range.RATE_RANGE), Range.RATE_RANGE);
        List<CurrencyRate> extrapolated = Forecast.calculateRate(rates, command.getTimeRange(), Range.RATE_RANGE);
        RatePrint.ratePrint(extrapolated, command,Range.RATE_RANGE);
    }
}
