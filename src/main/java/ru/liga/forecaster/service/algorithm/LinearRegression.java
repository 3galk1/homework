package ru.liga.forecaster.service.algorithm;


import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LinearRegression implements Algorithm {
    private double intercept, slope;
    private final double x = 31.0;

    public List<CurrencyRate> extrapolate(List<CurrencyRate> rates, Command command) {
        List<Double> days = new ArrayList<>();
        List<Double> course = new ArrayList<>();
        LocalDate inDate = command.getDate().minusMonths(1);
        for (CurrencyRate rate : rates) {
            if (rate.getDate().isBefore(inDate)) {
                break;
            }
            days.add((double) rate.getDate().getDayOfMonth());
            course.add(rate.getCourse().doubleValue() / (double) rate.getNominal());
        }
        for (int k = 1; k <= command.getTimeRange().getDays(); k++) {
            calcLinearRegression(days, course);
            rates.add(0, new CurrencyRate(
                    1,
                    (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1),
                    BigDecimal.valueOf(predict(x+k)),
                    rates.get(0).getCurrency()));
        }
        return rates;
    }

    public void calcLinearRegression(List<Double> x, List<Double> y) {
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

    public double predict(double x) {
        return slope * x + intercept;
    }


}