package ru.liga.homework.model;

import lombok.Getter;

@Getter
public class CommandForUse extends CurrencyType{

    private final int timeRange;

    public CommandForUse(String type , String path , int timeRange) {
        super(type , path);
        this.timeRange = timeRange;
    }
}
