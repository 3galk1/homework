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

    private CurrencyRate(CurrencyRateBuilder currencyRateBuilder) {
        nominal = currencyRateBuilder.nominal;
        date = currencyRateBuilder.date;
        course = currencyRateBuilder.course;
        currency = currencyRateBuilder.currency;
    }

    @Getter
    public static class CurrencyRateBuilder {
        private int nominal;
        private LocalDate date;
        private BigDecimal course;
        private String currency;

        public CurrencyRateBuilder nominal(int nominal) {
            this.nominal = nominal;
            return this;
        }

        public CurrencyRateBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public CurrencyRateBuilder course(BigDecimal course) {
            this.course = course;
            return this;
        }

        public CurrencyRateBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public CurrencyRate build() {
            return new CurrencyRate(this);
        }

    }

}
