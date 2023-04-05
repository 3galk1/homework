package ru.liga.forecaster.service;
import ru.liga.forecaster.model.type.Currency;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Output;
import ru.liga.forecaster.util.ListUtils;
import ru.liga.forecaster.util.reader.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRateApp {
    public static String StartForecaster(String messageFromBot) throws IOException {
        Command command = Parser.parseFromTelegramBot(messageFromBot);
        List<CurrencyRate> rates = Parser.parseFromCsv(CsvReader.readCsv(command.getCurrency().getFilePath()));
        rates = new Forecast().calculateRate(rates, command);
        return ListUtils.ratePrint(rates, command);
//        } else if (command.getOutput().equals(Output.GRAPH)) {
//            List<List<CurrencyRate>> extrapolated = new ArrayList<>();
//            List<String> currency = new ArrayList<>();
//            List<CurrencyRate> rate = new ArrayList<>();
//            for (int i = 0; i<command.getTimeRange().getDays();i++){
//                rate.add(rates.get(i));
//                currency.add(rates.get(i).getCurrency());
//            }
//            extrapolated.add(rate);
//            GraphUtils graphUtils = new GraphUtils().setData(extrapolated,currency);
//            return graphUtils.getCurrencyRatesAsGraph();
//        }
    }
}

