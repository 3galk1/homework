package ru.liga.forecastor.service;

import ru.liga.forecastor.model.Command;
import ru.liga.forecastor.model.type.Range;
import ru.liga.forecastor.util.reader.ReadCommand;
import ru.liga.forecastor.util.RatePrint;
import ru.liga.forecastor.util.reader.ReadCsv;

public class CurrencyRateApp {
    public static void StartForecaster() {
        Command command = new Parse().parserFromConsole(new ReadCommand().readCommand());
        new RatePrint().ratePrint(
                new Forecast().calculateRate(
                        new Parse().parserFromCsv(
                                ReadCsv.readCsv(command.getCurrency().getValue(), Range.RATE_RANGE),                              Range.RATE_RANGE) ,
                        command.getTimeRange(),Range.RATE_RANGE) ,
                command);
    }
}
