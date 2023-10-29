import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

     static String messageText = "test";

    public static String getMessageText() {
        return messageText;
    }

    @Override
    public String getBotToken() {
        return "6906636100:AAG4YHQ3sxfaI_pstJO3osfsYQ0JBJR0UqQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){ //если обновление имеет сообщение и сообщение является текстом
            Message message = update.getMessage(); //присвоение объекту сообщения от пользователя
            messageText = message.getText();

            long chatld = message.getChatId();// определение индетификатора чата, в который было отправлено сообщение
            SendMessage response = new SendMessage();// Объект для отправки ответного сообщения
            response.setChatId(String.valueOf(chatld));//установление индетификатора чата, куда будет отправлено сообщение
            response.setText("Hello. I am your GYM bot");
            System.out.println("Message from user " + message.getText() + "\n" + messageText);
            DataBase.connectToSQL();
            messageText = "";
            try{
                execute(response);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "GYM-bot";
    }
}
