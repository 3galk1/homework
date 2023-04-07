package ru.liga.forecaster.util;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateCourseList {
    public String createCourseForSend(List<CurrencyRate> data , Command command) {
        String courseList="";
        for (int i = command.getTimeRange().getDays() - 1; i >= 0; i--) {
            CurrencyRate rate = data.get(i);
            BigDecimal normalizedRate = convertToRealCourse(rate.getNominal() , rate.getCourse());
            String date = DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate());
            String formattedRate = normalizedRate.toString().replace("." , ",");
            courseList+=String.join(" - " , date , formattedRate)+"\n";
        }
        return courseList;
    }

    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 2 , RoundingMode.HALF_UP);
    }

}

