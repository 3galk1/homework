package ru.liga.forecastor.util.reader;

import java.util.Scanner;


public class ReadCommand {

    public String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}