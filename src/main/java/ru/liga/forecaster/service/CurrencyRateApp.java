package ru.liga.forecaster.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;
import ru.liga.forecaster.service.algorithm.Extrapolate;
import ru.liga.forecaster.service.algorithm.LastYear;
import ru.liga.forecaster.service.algorithm.LinearRegression;
import ru.liga.forecaster.service.algorithm.Mystical;
import ru.liga.forecaster.util.RatePrint;
import ru.liga.forecaster.util.reader.CsvReader;

import java.io.IOException;
import java.util.List;

public class CurrencyRateApp {

    public static String StartForecaster(Update update) throws IOException {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Command command = Parser.parseFromTelegramBot(update.getMessage().getText());
                List<CurrencyRate> rates = Parser.parseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath()));
                List<CurrencyRate> extrapolated = new Forecast().calculateRate(rates , command);
                return RatePrint.ratePrint(extrapolated , command);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

