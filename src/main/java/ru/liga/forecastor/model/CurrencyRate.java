package ru.liga.forecastor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CurrencyRate {
    private final int nominal;
    private final String currency;
    private final LocalDate date;
    private final BigDecimal course;

    public CurrencyRate (int nominal, LocalDate date, BigDecimal course, String currency) {
        this.nominal = nominal;
        this.date = date;
        this.course = course;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                ", nominal='" + nominal + '\'' +
                "date='" + date + '\'' +
                ", course='" + course + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
