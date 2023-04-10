package ru.liga.forecaster.service.algorithm;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LinearRegression implements ForecastAlgorithm {
    private double intercept, slope;
    private final int firstRate = 0;
    private final List<Double> days = new ArrayList<>();
    private final List<Double> course = new ArrayList<>();

    private void ExtrapolatedOnTomorrow(List<CurrencyRate> rates , Command command) {
        LocalDate endDate = command.getDate().minusMonths(1);
        for (CurrencyRate rate : rates) {
            if (rate.getDate().isBefore(endDate)) {
                break;
            }
            days.add((double) rate.getDate().getDayOfMonth());
            course.add(rate.getCourse().doubleValue() / (double) rate.getNominal());
        }
    }

    public List<CurrencyRate> ExtrapolatedOnTimeRange(List<CurrencyRate> rates , Command command) {
        ExtrapolatedOnTomorrow(rates , command);
        for (int k = 1; k <= command.getTimeRange().getDays(); k++) {
            calcLinearRegression(days , course);
            WriteExtrapolatedRate(rates , k);
        }
        return rates;
    }

    private void WriteExtrapolatedRate(List<CurrencyRate> rates , int k) {
        double x = 31.0;
        rates.add(firstRate , new CurrencyRate(
                1 ,
                CheckDate(rates) ,
                BigDecimal.valueOf(predict(x + k)) ,
                rates.get(firstRate).getCurrency()));
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
        return (rates.get(firstRate).getDate().isBefore(LocalDate.now()) ? LocalDate.now() :
                rates.get(firstRate).getDate()).plusDays(1);
    }

}