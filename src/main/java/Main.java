import ru.liga.homework.*;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        FileReader readCSV = new FileReader();
        Parser parse = new Parser();
        Forecast forecast = new Forecast();
        Formation formation = new Formation();

        Scanner in = new Scanner(System.in);
        String [] parsedCommand = parse.parsed(in.nextLine());
        List <CurrencyRate> dataFromCsv = readCSV.getDataFromCsvFile(parsedCommand[1]);
        if (parsedCommand[2].toLowerCase().equals("tomorrow")) {
            formation.formateDataOnTomorrow(forecast.calculateRateOnTommorow(dataFromCsv));
        }
        else if (parsedCommand[2].toLowerCase().equals("week")) {
            formation.formateDataOnWeek(forecast.calculateRateOnWeek(dataFromCsv));
        }
        }
    }