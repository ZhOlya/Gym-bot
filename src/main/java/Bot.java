import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class Bot extends TelegramLongPollingBot {

    private static final Logger loggerBot = LoggerFactory.getLogger(Bot.class);

     private static String messageText = "";

     private static Long idChat;

    public static Long getIdChat() {
        return idChat;
    }

    public static String getMessageText() {
        return messageText;
    }

    @Override
    public String getBotToken() {
        return "6906636100:AAG4YHQ3sxfaI_pstJO3osfsYQ0JBJR0UqQ";
    }

    static SendMessage response = new SendMessage();// Объект для отправки ответного сообщения

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){ //если обновление имеет сообщение и сообщение является текстом
            Message message = update.getMessage(); //присвоение объекту сообщения от пользователя
            messageText = message.getText();
            idChat = message.getChatId(); // определение индетификатора чата, в который было отправлено сообщение
            response.setChatId(String.valueOf(idChat));//установление индетификатора чата, куда будет отправлено сообщение
            DataBase.connectToSQL();
            try{
                execute(response);
            } catch (TelegramApiException e){
                loggerBot.error("Error occurred in Bot class (update)", e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "GYM-bot";
    }


    public void sendGraph(String chatId) {
        response.setText("This image shows a graph of your activity");
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(new File("C:\\Personal\\Olga\\Study\\Gym-bot\\graph\\Exercises_Chart.png")));

        try {
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            loggerBot.error("Error occurred in Bot class (sendGraph) ", e);
        }
    }
}
