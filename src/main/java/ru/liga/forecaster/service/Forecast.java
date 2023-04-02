package ru.liga.forecaster.service;

import lombok.Getter;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;
import ru.liga.forecaster.service.algorithm.Extrapolate;
import ru.liga.forecaster.service.algorithm.LastYear;
import ru.liga.forecaster.service.algorithm.LinearRegression;
import ru.liga.forecaster.service.algorithm.Mystical;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Forecast {
    private final Extrapolate extrapolate;
    private final LastYear lastYear;
    private final Mystical mystical;

    public Forecast(Extrapolate extrapolate , LastYear lastYear , Mystical mystical) {
        this.extrapolate = extrapolate;
        this.lastYear = lastYear;
        this.mystical = mystical;
    }

    public List<CurrencyRate> calculateRate(List<CurrencyRate> rates , Command command, Range rateRange) {
        //mystical.calculateMysticalRate(rates, command);
        //lastYear.calculateLastYearRate(rates, command);
        //extrapolate.extrapolate(rates , timeRange , rateRange);
        return rates;
    }
    public List<CurrencyRate> calcLinearRegression(List<CurrencyRate> rates , Command command) {
        List<Double> daysForRegres = new ArrayList<>();
        List<Double> courseForRegres = new ArrayList<>();
        LocalDate inDate = LocalDate.now().minusMonths(1);
        for (CurrencyRate rate : rates) {
            if (rate.getDate().isBefore(inDate)) {
                break;
            }
            daysForRegres.add((double) rate.getDate().getDayOfMonth());
            courseForRegres.add(rate.getCourse().doubleValue()/(double)rate.getNominal()); //номинал 1
        }
        LinearRegression linear = new LinearRegression(daysForRegres,courseForRegres);
        double slope = linear.slope();
        double intercept = linear.intercept();
        double predict = linear.predict(1.0);
        rates.add(0 , new CurrencyRate(
                1,
                (rates.get(0).getDate().isBefore(LocalDate.now()) ? LocalDate.now() : rates.get(0).getDate()).plusDays(1) ,
                rates.get(0).getCourse() ,
                rates.get(0).getCurrency()));
        return rates;
    }
}

