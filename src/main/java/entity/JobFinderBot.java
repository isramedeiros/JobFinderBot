package entity;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import services.JobFinderBotService;

//
public class JobFinderBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public JobFinderBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        // we check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // creating the send message object
            JobFinderBotService jobFinderBotService = new JobFinderBotService();
            String message_text = update.getMessage().getText();

            String json = jobFinderBotService.getJobs(message_text);
            String responseText = jobFinderBotService.parseJobs(json);

            long chat_id = update.getMessage().getChatId();

            // creating a new message object
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(responseText)
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            System.out.println("Received update: " + update);
        }
    }
}