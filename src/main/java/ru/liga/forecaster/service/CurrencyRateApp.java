package ru.liga.forecaster.service;
//todo неоптимизированные импорты

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.repository.CsvReader;

import java.io.IOException;
import java.util.List;

public class CurrencyRateApp {
    //todo наименование метода с большой буквы
    public List<CurrencyRate> StartForecaster(String messageFromBot) throws IOException {
        Command command = Parser.parseFromTelegramBot(messageFromBot);
        List<CurrencyRate> rates = Parser.parseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath()));
        return rates;
    }
}

