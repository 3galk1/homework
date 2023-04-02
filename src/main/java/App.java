import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, TelegramApiException {
        ru.liga.forecaster.service.CurrencyRateApp.StartForecaster();
    }
}