package ru.liga.forecaster.util;

import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateCourseList {

    public String createCurrencyCourseList(List<List<CurrencyRate>> data , Command command) {
        StringBuilder courseList = new StringBuilder();
        for (List<CurrencyRate> cur : data) {
            createOneCurrencyCourse(command , courseList , cur);
        }
        return courseList.toString();
    }

    private void createOneCurrencyCourse(Command command , StringBuilder courseList , List<CurrencyRate> cur) {
        int period = command.getTimeRange().getDays();
        for (int day = period - 1; day >= 0; day--) {
            CurrencyRate rate = cur.get(day);
            BigDecimal normalizedRate = convertToRealCourse(cur.get(day).getNominal() , cur.get(day).getCourse());
            String date = DateTimeFormatter.ofPattern("E dd.MM.yyyy").format(rate.getDate());
            String formattedRate = normalizedRate.toString().replace("." , ",");
            courseList.append(String.join(" - " , date , formattedRate)).append("\n");
        }
        courseList.append("\n");
    }

    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = BigDecimal.valueOf((double) nominal);
        return course.divide(nominalDivisor , 4 , RoundingMode.HALF_UP);
    }

}

