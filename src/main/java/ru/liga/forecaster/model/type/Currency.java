package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum Currency {
    EUR("currency/EUR.csv" , "EUR"),
    TRY("currency/TRY.csv" , "TRY"),
    USD("currency/USD.csv" , "USD"),
    AMD("currency/AMD.csv" , "AMD"),
    BGN("currency/BGN.csv" , "BGN");

    private final String filePath;
    private final String name;

    public static final Map<String, Currency> currencyMap;

    static {
        currencyMap = new HashMap<>();
        for (Currency arg : Currency.values()) {
            currencyMap.put(arg.name , arg);
        }
    }

    public static boolean findByName(String name) {
        return currencyMap.containsKey(name);
    }
}