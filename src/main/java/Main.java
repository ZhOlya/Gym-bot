import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws TelegramApiException {

/*        UserMessage.treatmentText("nok lo lo");
        System.out.println(UserMessage.getParThree());*/

//        DataBase.connectToSQL();




        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(new Bot());

        }catch (TelegramApiException e){
            e.printStackTrace();
        }



    }
}
