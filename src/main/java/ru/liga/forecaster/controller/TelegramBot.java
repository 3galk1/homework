package ru.liga.forecaster.controller;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.forecaster.exception.DataErrorException;
import ru.liga.forecaster.exception.IncorrectCommandException;
import ru.liga.forecaster.mapper.CommandMapper;
import ru.liga.forecaster.model.Command;
import ru.liga.forecaster.model.type.Output;
import ru.liga.forecaster.service.ForecasterApp;
import ru.liga.forecaster.service.parser.Parser;
import ru.liga.forecaster.util.CreateCourseGraph;
import ru.liga.forecaster.util.CreateCourseList;
import ru.liga.forecaster.util.TelegramConstants;

import java.io.File;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class TelegramBot extends TelegramLongPollingBot {
    private final CreateCourseList createCourseList;
    private final CreateCourseGraph createCourseGraph;
    private final ForecasterApp forecasterApp;
    private Command command;
    private final CommandMapper commandMapper;
    private final TelegramConstants telegramConstants;
    private static final String CURRENCY = "currency", OUTPUT = "output", ERROR = "error";

    @Override
    public String getBotUsername() {
        return telegramConstants.getBot_name();
    }

    @Override
    public String getBotToken() {
        return telegramConstants.getBot_token();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String originalMessage = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            try {
                handlingUserMessage(originalMessage , chatId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlingUserMessage(String originalMessage , String chatId) throws TelegramApiException {
        if (originalMessage.equals("/Start")) {
            sendText(chatId , "Введитe команду:");
        } else {
            try {
                Map<String, String> parseArguments = Parser.parseArguments(originalMessage);
                if (parseArguments.containsKey(ERROR)) {
                    sendText(chatId , parseArguments.get(ERROR));
                } else {
                    command = commandMapper.MapCommand(parseArguments);
                    if (command.getOutput().equals(Output.GRAPH)) {
                        sendImage(chatId , createCourseGraph.createRatesGraph(
                                forecasterApp.StartForecaster(command) , command.getCurrency()));
                    } else {
                        sendText(chatId , createCourseList.createCurrencyCourseList(
                                forecasterApp.StartForecaster(command) , command));
                    }
                }
            } catch (DataErrorException e) {
                sendText(chatId , e.getMessage() + " " + originalMessage);
            } catch (Exception ignored) {
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

}
