package ru.liga.forecaster.controller;


import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.forecaster.service.CurrencyRateApp;


public class TelegramBot extends TelegramLongPollingBot {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String originalMessage = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            if (originalMessage.equals("/Start")) {
                setAnswer(chatId, "Привет ^_^" + "\n"+"Введитe команду:");
            } else {
                try {
                    setAnswer(chatId, CurrencyRateApp.StartForecaster(originalMessage));
                } catch (RuntimeException e) {
                    setErrorMessage(chatId, e.getMessage() + originalMessage);
                }
            }
        }
    }

    private void setAnswer(String chatId, String text) throws TelegramApiException {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId);
        this.execute(answer);
    }

    private void setErrorMessage(String chatId, String text) throws TelegramApiException {
        SendMessage errorMessage = new SendMessage();
        errorMessage.setText(text);
        errorMessage.setChatId(chatId);
        this.execute(errorMessage);

    }


    @Override
    public String getBotUsername() {
        return "CourseForecasterBot";
    }

    @Override
    public String getBotToken() {
        return "6223395355:AAE45DGF-3Imez1mAv5jZGoUoW1T3s7grVs";
    }

}
