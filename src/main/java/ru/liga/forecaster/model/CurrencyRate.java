package ru.liga.forecaster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor

@Getter
public class CurrencyRate {
    private final int nominal;
    private final LocalDate date;
    private final BigDecimal course;
    private final String currency;

}
