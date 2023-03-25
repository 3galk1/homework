package ru.liga.homework.model.type;

import lombok.Getter;

@Getter
public enum CurrencyConstants {
    EUR("EUR"),EUR_PATH("C:/JAVA/homework/src/main/resources/eur.csv"),
    TRY("TRY"),TRY_PATH("C:/JAVA/homework/src/main/resources/try.csv"),
    USD("USD"),USD_PATH("C:/JAVA/homework/src/main/resources/usd.csv");

    private String value;

    CurrencyConstants(String value) {
        this.value = value;
    }
}
