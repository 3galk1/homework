package ru.liga.homework.util;

import ru.liga.homework.model.CommandForUse;
import ru.liga.homework.model.type.TimeRange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class FileReader {

    public String readCommand() {
        System.out.println("Введите команду:");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        return command;
    }

    public List<String> getDataFromCsvFile(CommandForUse command) {
        try {
            List<String> dataFromCsv = new ArrayList<>();
            BufferedReader csvReader = new BufferedReader(new java.io.FileReader(command.getPath()));
            String row = csvReader.readLine(); //чтение первой строки с Headers
            for (int i = 0; i < TimeRange.WEEK.getValue(); i++) {
                dataFromCsv.add(csvReader.readLine());
            }
            return dataFromCsv;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден " + e);
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + e);
            return null;
        }
    }
}
//            String row = csvReader.readLine(); //чтение первой строки с Headers
//            for (int i = 0; i < 7; i++) {
//                    String[] line = csvReader.readLine().split(";");
//                    rate.add(new CurrencyRate(Integer.parseInt(line[0].replace("\"","")) ,
//                            LocalDate.parse(line[1].replace("\"","") , formatter) ,
//                            BigDecimal.valueOf(Double.parseDouble(line[2].replace("\"","").replace("," , "."))) ,
//                            line[3].replace("\"","")));
//            }
//            return rate;
//        }
//        else {throw new RuntimeException("File is not Exists");}
//    }

