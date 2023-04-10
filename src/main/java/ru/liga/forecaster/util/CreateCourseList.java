package ru.liga.forecaster.util;

import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class CreateCourseList {
    public String CreateCourseList(List<List<CurrencyRate>> data , Map<String, String> arguments) {
        StringBuilder courseList = new StringBuilder();
        for (List<CurrencyRate> cur : data) {
            for (int i = Range.valueOf(arguments.get("period")).getDays() - 1; i >= 0; i--) {
                CurrencyRate rate = cur.get(i);
                BigDecimal normalizedRate = convertToRealCourse(cur.get(i).getNominal() , cur.get(i).getCourse());
                String date = DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate());
                String formattedRate = normalizedRate.toString().replace("." , ",");
                courseList.append(String.join(" - " , date , formattedRate)).append("\n");
            }
            courseList.append("\n");
        }
        return courseList.toString();
    }

    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 4 , RoundingMode.HALF_UP);
    }

}

