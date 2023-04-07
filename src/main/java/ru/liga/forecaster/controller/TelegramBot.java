package ru.liga.forecaster.controller;


import lombok.Data;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Output;
import ru.liga.forecaster.service.CurrencyRateApp;
import ru.liga.forecaster.service.Parser;
import ru.liga.forecaster.util.CreateCourseGraph;
import ru.liga.forecaster.util.CreateCourseList;

import java.io.File;
import java.util.List;

@Data
public class TelegramBot extends TelegramLongPollingBot {
    private Command command;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String originalMessage = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();


            if (originalMessage.equals("/Start")) {
                sendAnswerMessage(chatId, "Привет ^_^" + "\n" + "Введитe команду:");
            } else {
                try {
                    command = Parser.parseFromTelegramBot(originalMessage);
                    List<CurrencyRate> rates = new CurrencyRateApp().StartForecaster(originalMessage);
                    if (command.getOutput().equals(Output.GRAPH)) {
                        sendImage(chatId, new CreateCourseGraph().createRateGraph(rates, command));
                    } else {
                        sendAnswerMessage(chatId, new CreateCourseList().createCourseForSend(rates, command));
                    }
                } catch (RuntimeException e) {
                    sendAnswerMessage(chatId, e.getMessage() + originalMessage);
                }
            }
        }
    }

    private void sendAnswerMessage(String chatId, String text) throws TelegramApiException {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId);
        this.execute(answer);
    }

    private void sendImage(String chatId, File file) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(file));
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
