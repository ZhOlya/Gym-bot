import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    private static final Logger loggerMain = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBot.registerBot(new Bot());

        }catch (TelegramApiException e){
            loggerMain.error("Error occurred on Main class", e);
        }

    }
}
