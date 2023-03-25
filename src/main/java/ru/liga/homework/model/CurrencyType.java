package ru.liga.homework.model;

import lombok.Getter;


@Getter
public class CurrencyType {

    private final String type;
    private final String path;

    public CurrencyType(String type , String path) {
        this.type = type;
        this.path = path;
    }

}
