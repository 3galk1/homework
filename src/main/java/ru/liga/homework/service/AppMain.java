package ru.liga.homework.service;

import ru.liga.homework.model.CommandForUse;
import ru.liga.homework.util.FileReader;
import ru.liga.homework.util.RatePrint;

public class AppMain {
    public static void StartForcastCurrencyRate() {
        CommandForUse command = new CommandParser().parserCommandFromConsole(new FileReader().readCommand());
        if (command.getTimeRange() == 1) {
            new RatePrint().ratePrintOnTomorrow(
                    new RateForecastService().calculateRate(
                            new CommandParser().parserDatafromCsv(
                                    new FileReader().getDataFromCsvFile(command)) , command));
        } else {
            new RatePrint().ratePrintOnWeek(
                    new RateForecastService().calculateRate(
                            new CommandParser().parserDatafromCsv(
                                    new FileReader().getDataFromCsvFile(command)) , command));
        }
    }
}
