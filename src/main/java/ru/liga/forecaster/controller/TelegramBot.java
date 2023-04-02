package ru.liga.forecaster.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
        getMessage(update);}
    }

    @Override
    public String getBotUsername() {
        return "CourseForecasterBot";
    }

    @Override
    public String getBotToken() {
        return "6223395355:AAE45DGF-3Imez1mAv5jZGoUoW1T3s7grVs";
    }
    public String getMessage(Update update){
        return update.getMessage().getText();
    }
}
