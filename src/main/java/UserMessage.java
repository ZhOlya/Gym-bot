import org.jetbrains.annotations.NotNull;

public class UserMessage {

    static String partOne = "";
    static String partTwo = "";
    static String parThree = "";

    static String command = "";

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

    //–азделение сообщени€ пользовател€ на первое слово (команда) и остальную часть

    public static void definingCommandAndRest (String text){
        String trimText = text.trim();
        int indexSplit = trimText.indexOf(' ');
        command = trimText.substring(0,indexSplit);
        String threePartText = trimText.substring(indexSplit + 1);
        if (command.equalsIgnoreCase("add")){
            splitTextForAdd(threePartText);
        }
        System.out.println("Class UserMessage, method definingCommandAndRest. " + command + " " + threePartText);
    }


    //–азделение текста на три составл€ющие
    public static void splitTextForAdd(String text){
        if (treatmentTextBool(text)){
            int spaceIndexOne = text.indexOf(' ');
            int spaceIndexTwo = text.lastIndexOf(' ');
            partOne = text.substring(0, spaceIndexOne);
            partTwo = text.substring(spaceIndexOne + 1, spaceIndexTwo);
            parThree = text.substring(spaceIndexTwo + 1);
            System.out.println("Class UserMessage, text and method splitText: " + text + " " + partOne + partTwo + parThree);
        } else {
            System.out.println("Sorry. Input message doesn`t has three parts." + partOne + partTwo + parThree);
        }
    }

    public static void clearPartsOfText(){
        partOne = "";
        partTwo = "";
        parThree = "";
    }
    //ћетод, котороый првер€ет сообщател€ на количество пробелов. ≈сли их два, то возвращает текст, если нет то возращает ??????7??
    static boolean treatmentTextBool (@NotNull String text){
        int countSpace = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' '){
                countSpace++;
            }
        }
        return countSpace == 2;
    }
//    public static void treatmentTextVoid (String text){
//        int countSpace = 0;
//        for (int i = 0; i < text.length(); i++) {
//            if (text.charAt(i) == ' '){
//                countSpace++;
//            }
//            if (countSpace == 2){
//                splitText(text);
//            } else {
//                System.out.println("Wrong enter message");
//            }
//        }
//    }

}
