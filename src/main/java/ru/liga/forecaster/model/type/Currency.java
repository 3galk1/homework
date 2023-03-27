package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    EUR("eur.csv"),
    TRY("try.csv"),
    USD("usd.csv");

    private String filePath;


}