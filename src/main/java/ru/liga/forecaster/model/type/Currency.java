package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    EUR("currency/EUR.csv"),
    TRY("currency/TRY.csv"),
    USD("currency/USD.csv"),
    AMD("currency/AMD.csv"),
    BGN("currency/BGN.csv");

    private final String filePath;


}