package ru.liga.forecaster;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.forecaster.controller.TelegramBot;
import ru.liga.forecaster.mapper.CommandMapper;
import ru.liga.forecaster.service.ForecasterApp;
import ru.liga.forecaster.util.CreateCourseGraph;
import ru.liga.forecaster.util.CreateCourseList;
import ru.liga.forecaster.util.TelegramConstants;

public class App {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
        telegramBot.registerBot(new TelegramBot(
                new CreateCourseList(),
                new CreateCourseGraph(),
                new ForecasterApp(),
                new CommandMapper(),
                new TelegramConstants()));
    }
}