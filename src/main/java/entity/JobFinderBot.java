package entity;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class JobFinderBot implements LongPollingSingleThreadUpdateConsumer {
    @Override
    public void consume(Update update) {
        // we check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
        }
    }
}
