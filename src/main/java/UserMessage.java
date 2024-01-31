import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMessage {

    private static final Logger loggerUseMessage = LoggerFactory.getLogger (UserMessage.class);

    private static String partOne = "";
    private static String partTwo = "";
    private static String parThree = "";

    private static String command = "";

    public static String getCommand() {
        return command;
    }

    public static String getPartOne() {
        return partOne;
    }

    public static String getPartTwo() {
        return partTwo;
    }

    public static String getParThree() {
        return parThree;
    }

    //Разделение сообщения пользователя на первое слово (команда) и остальную часть

    public static void definingCommandAndRest (String text){
        if (text.isEmpty()){
            Bot.response.setText("Sorry, I don`t understand you");
        }
        else {
            String trimText = text.trim();
            if (!trimText.contains(" ")) {
                //проверка первого символа, если это "/", то удаляем его
                char firstSymbol = trimText.charAt(0);
                if (firstSymbol == '/') {
                    command = trimText.substring(1);
                } else {
                    command = trimText;
                }
//                System.out.println("Class UserMessage, method definingCommandAndRest. Command is " + command + "\n");
            } else {
                int indexSplit = trimText.indexOf(' ');
                command = trimText.substring(0, indexSplit);
                String threePartText = trimText.substring(indexSplit + 1);
                if (command.equalsIgnoreCase("add")) {//Возможно, эта проверка и не нужна, потому что имеется только одна команда, содержащая пробелы ADD
                    splitTextForAdd(threePartText);
//                    System.out.println("Class UserMessage, method definingCommandAndRest. Command is " + command +
//                            ". Add`s data are " + threePartText + "\n");
                }
            }
        }
    }


    //Разделение текста на три составляющие
    public static void splitTextForAdd(String text){
        if (treatmentTextBool(text)){
            int spaceIndexOne = text.indexOf(' ');
            int spaceIndexTwo = text.lastIndexOf(' ');
            partOne = text.substring(0, spaceIndexOne).toLowerCase();
            partTwo = text.substring(spaceIndexOne + 1, spaceIndexTwo);
            parThree = text.substring(spaceIndexTwo + 1);
            System.out.println("Class UserMessage, text and method splitText: " + text + " " + partOne + partTwo + parThree + "\n");
        } else {
            System.out.println("Sorry. Input message doesn`t has three parts." + partOne + partTwo + parThree + "\n");
            Bot.response.setText("Sorry. Input message doesn`t has three parts.");
        }
    }

    //Метод, котороый прверяет сообщение на количество пробелов.
    static boolean treatmentTextBool (@NotNull String text){
        int countSpace = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' '){
                countSpace++;
            }
        }
        return countSpace == 2;
    }

}
