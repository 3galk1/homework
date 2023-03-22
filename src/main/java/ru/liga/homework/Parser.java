package ru.liga.homework;

public class Parser {
    public String [] parsed (String command) {
        switch (command.toLowerCase()) {
            case "rate try tomorrow":
            case "rate eur tomorrow":
            case "rate usd tomorrow":
            case "rate eur week":
            case "rate usd week":
            case "rate try week":
                return command.split(" ");
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    }
