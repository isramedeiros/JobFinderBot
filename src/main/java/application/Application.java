package application;

import entity.JobFinderBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.github.cdimascio.dotenv.Dotenv;

public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String botToken = dotenv.get("BOT_TOKEN");

        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

        try {
            botsApplication.registerBot(botToken, new JobFinderBot(botToken));
            System.out.println("Bot successfully started! ");
            new java.util.concurrent.CountDownLatch(1).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}