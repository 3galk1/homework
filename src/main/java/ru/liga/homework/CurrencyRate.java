package ru.liga.homework;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate {
    private int nominal;
    private String currency;
    private LocalDate date;
    private BigDecimal course;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNominal() {
        return nominal;
    }

    public BigDecimal getCourse() {
        return course;
    }

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
