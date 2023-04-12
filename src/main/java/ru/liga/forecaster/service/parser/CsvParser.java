package ru.liga.forecaster.service.parser;

import lombok.extern.slf4j.Slf4j;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
public class CsvParser {
    private static final int FIRST_INDEX = 0, SECOND_INDEX = 1, THIRD_INDEX = 2, FOURTH_INDEX = 3;
    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().append(ofPattern("dd.MM.yyyy")).toFormatter();


    public static List<CurrencyRate> parseFromCsv(List<String> dataFromCsv) {
        log.info("Старт Csv парсера");
        List<CurrencyRate> rates = new ArrayList<>();
        for (String s : dataFromCsv) {
            String[] parsedData = s.split(";");
            rates.add(new CurrencyRate.CurrencyRateBuilder()
                    .nominal(Integer.parseInt(parsedData[FIRST_INDEX]
                            .replace(" " , "").replace("\"" , "")))
                    .date(LocalDate.parse(parsedData[SECOND_INDEX]
                            .replace("\"" , "") , formatter))
                    .course(BigDecimal.valueOf(Double.parseDouble(parsedData[THIRD_INDEX].
                            replace("\"" , "").replace("," , "."))))
                    .currency(parsedData[FOURTH_INDEX]
                            .replace("\"" , ""))
                    .build());
        }
        log.info("Завершение работы Csv парсера");
        return rates;
    }
}
