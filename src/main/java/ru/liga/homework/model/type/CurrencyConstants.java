package ru.liga.homework.model.type;

import lombok.Getter;

@Getter
public enum CurrencyConstants {
    EUR("EUR" , "C:/JAVA/homework/src/main/resources/eur.csv"),
    TRY("TRY" , "C:/JAVA/homework/src/main/resources/try.csv"),
    USD("USD" , "C:/JAVA/homework/src/main/resources/usd.csv");

    private String value;
    private String name;

    CurrencyConstants(String name , String value) {
        this.value = value;
        this.name = name;
    }
}