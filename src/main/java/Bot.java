import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

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

    static SendMessage response = new SendMessage();// ������ ��� �������� ��������� ���������

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){ //���� ���������� ����� ��������� � ��������� �������� �������
            Message message = update.getMessage(); //���������� ������� ��������� �� ������������
            messageText = message.getText();
            idChat = message.getChatId();

            long chatld = message.getChatId();// ����������� �������������� ����, � ������� ���� ���������� ���������

            response.setChatId(String.valueOf(chatld));//������������ �������������� ����, ���� ����� ���������� ���������

//            response.setText("Hello. I am your GYM bot");
            DataBase.connectToSQL();
//            System.out.println("Class BOT. Message from user: " + messageText + ". Id chat: " + idChat + "\n"); //Check
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
