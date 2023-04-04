package ru.liga.forecaster.util;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RatePrint {
    public static String ratePrint(List<CurrencyRate> data , Command command) {
            String printCourse="";
        for (int i = command.getTimeRange().getDays() - 1; i >= 0; i--) {
            CurrencyRate rate = data.get(i);
            BigDecimal normalizedRate = convertToRealCourse(rate.getNominal() , rate.getCourse());
            String date = DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate());
            String formattedRate = normalizedRate.toString().replace("." , ",");
            printCourse+=String.join(" - " , date , formattedRate)+"\n";
        }
        return printCourse;
    }

    private static BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 2 , RoundingMode.HALF_UP);
    }

}

