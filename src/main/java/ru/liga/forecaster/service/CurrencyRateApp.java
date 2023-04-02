package ru.liga.forecaster.service;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.forecaster.controller.TelegramBot;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;
import ru.liga.forecaster.service.algorithm.Extrapolate;
import ru.liga.forecaster.service.algorithm.LastYear;
import ru.liga.forecaster.service.algorithm.Mystical;
import ru.liga.forecaster.util.reader.CommandReader;
import ru.liga.forecaster.util.RatePrint;
import ru.liga.forecaster.util.reader.CsvReader;

import java.io.IOException;
import java.util.List;

public class CurrencyRateApp {
    public static void StartForecaster() throws IOException, TelegramApiException {
        TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
        telegramBot.registerBot(new TelegramBot());
       Command command = Parser.parseFromTelegramBot();
       //Command command = Parser.parseFromConsole(CommandReader.readCommand());
        List<CurrencyRate> rates = Parser.parseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath()) , Range.RATE_RANGE);
        List<CurrencyRate> extrapolated = new Forecast(new Extrapolate() , new LastYear() , new Mystical())
                .calcLinearRegression(rates,command);
        RatePrint.ratePrint(extrapolated , command);
        StartForecaster();
    }
}
