package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LinearRegression implements ForecastAlgorithm {
    private double intercept, slope;
    private static final int FIRST_RATE = 0;
    private final List<Double> days = new ArrayList<>();
    private final List<Double> course = new ArrayList<>();

    public List<CurrencyRate> extrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        extrapolatedOnTomorrow(rates , command);
        for (int k = 1; k <= command.getTimeRange().getDays(); k++) {
            calcLinearRegression(days , course);
            writeExtrapolatedRate(rates , k);
        }
        if (!command.getDate().equals(LocalDate.now())) {
            extrapolatedOnDate(rates , command);
        }
        return rates;
    }

    private void extrapolatedOnDate(List<CurrencyRate> rates , Command command) {
        LocalDate endDate = command.getDate();
        LocalDate currentDate = LocalDate.now();
        int count = 0;
        while (currentDate.isBefore(endDate)) {
            currentDate = currentDate.plusDays(count);
            count++;
        }
        writeExtrapolatedRate(rates , count);
    }

    private void extrapolatedOnTomorrow(List<CurrencyRate> rates , Command command) {
        LocalDate startDate = LocalDate.now().minusMonths(1);
        for (CurrencyRate rate : rates) {
            if (rate.getDate().isBefore(startDate)) {
                break;
            }
            days.add((double) rate.getDate().getDayOfMonth());
            course.add(rate.getCourse().doubleValue() / (double) rate.getNominal());
        }
    }

    private void writeExtrapolatedRate(List<CurrencyRate> rates , int k) {
        double x = 31.0;
        rates.add(FIRST_RATE , new CurrencyRate.CurrencyRateBuilder()
                .nominal(1)
                .date(CheckDate(rates))
                .course(BigDecimal.valueOf(predict(x + k)))
                .currency(rates.get(FIRST_RATE).getCurrency())
                .build());
    }

    private void calcLinearRegression(List<Double> x , List<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.size();

        double sumx = 0.0, sumy = 0.0;
        for (int i = 0; i < n; i++) {
            sumx += x.get(i);
            sumy += y.get(i);
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        double xxbar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x.get(i) - xbar) * (x.get(i) - xbar);
            xybar += (x.get(i) - xbar) * (y.get(i) - ybar);
        }
        slope = xybar / xxbar;
        intercept = ybar - slope * xbar;
    }

    private double predict(double x) {
        return slope * x + intercept;
    }

    private LocalDate CheckDate(List<CurrencyRate> rates) {
        return (rates.get(FIRST_RATE).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(FIRST_RATE).getDate()).plusDays(1);
    }

}