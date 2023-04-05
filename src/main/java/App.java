import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.forecaster.controller.TelegramBot;
import ru.liga.forecaster.service.CurrencyRateApp;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
        telegramBot.registerBot(new TelegramBot());
    }
}