package ru.liga.homework.service;

import ru.liga.homework.model.Command;
import ru.liga.homework.util.Reader;
import ru.liga.homework.util.RatePrint;

public class CurrencyRateApp {
    public static void StartForecaster() {
        Command command = new Parser().parserFromConsole(new Reader().readCommand());
        new RatePrint().ratePrint(
                new Forecaster().calculateRate(
                        new Parser().parserFromCsv(
                                new Reader().readCsv(command)) , command), command);
    }
}
