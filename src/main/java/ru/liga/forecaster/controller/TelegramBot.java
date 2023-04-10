package ru.liga.forecaster.controller;


import lombok.Data;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.forecaster.model.type.Output;
import ru.liga.forecaster.service.ForecasterApp;
import ru.liga.forecaster.service.Parser;
import ru.liga.forecaster.util.CreateCourseGraph;
import ru.liga.forecaster.util.CreateCourseList;

import java.io.File;
import java.util.Map;

@Data
public class TelegramBot extends TelegramLongPollingBot {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String originalMessage = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            handlingUserMessage(originalMessage , chatId);
        }
    }

    private void handlingUserMessage(String originalMessage , String chatId) throws TelegramApiException {
        if (originalMessage.equals("/Start")) {
            sendText(chatId , "Привет ^_^" + "\n" + "Введитe команду:");
        } else {
            try {
                Map<String, String> arguments = Parser.ParseMessage(originalMessage);
                String currency = arguments.get("currency");
                if (arguments.get("output").equals(Output.GRAPH.name())) {
                    sendImage(chatId , new CreateCourseGraph()
                            .CreateRatesGraph(ForecasterApp.StartForecaster(arguments) , currency));
                } else {
                    sendText(chatId , new CreateCourseList()
                            .CreateCourseList(ForecasterApp.StartForecaster(arguments) , arguments));
                }
            } catch (RuntimeException e) {
                sendText(chatId , e.getMessage() + originalMessage);
            } catch (Exception e) {

            }
        }
    }

    private void sendText(String chatId , String text) throws TelegramApiException {
        SendMessage sendText = new SendMessage();
        sendText.setText(text);
        sendText.setChatId(chatId);
        this.execute(sendText);
    }

    private void sendImage(String chatId , File file) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(file));
        this.execute(sendPhoto);
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
