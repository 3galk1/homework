package ru.liga.forecaster.util.reader;

import java.util.Scanner;


public class CommandReader {

    public static String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}