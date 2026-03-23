package application;

import entity.JobFinderBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.github.cdimascio.dotenv.Dotenv;

public class Application {
    public static void main(String[] args) {
        // instantiating the application
        Dotenv dotenv = Dotenv.load();
        String botToken = dotenv.get("BOT_TOKEN");

        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, new JobFinderBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
