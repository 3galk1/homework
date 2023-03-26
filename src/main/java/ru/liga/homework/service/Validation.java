package ru.liga.homework.service;

public class Validation {

    public boolean validationDataFromConsole(String command) {
        switch (command.toLowerCase()) {
            case "rate eur tomorrow":
            case "rate eur week":
            case "rate try tomorrow":
            case "rate try week":
            case "rate usd tomorrow":
            case "rate usd week":
                return true;
            default:
                System.out.println("Неверная команда " + command);  // временно, будет заменен на ошибку
                return false;
        }
    }
}
