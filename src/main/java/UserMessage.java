import org.jetbrains.annotations.NotNull;

public class UserMessage {

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

    //–азделение сообщени€ пользовател€ на первое слово (команда) и остальную часть



    public static void definingCommandAndRest (String text){
        String trimText = text.trim();
        if (!trimText.contains(" ")){
            //проверка первого символа, если это "/", то удал€ем его
            char firstSymbol = trimText.charAt(0);
            if (firstSymbol == '/'){
                command = trimText.substring(1);
            }else {
                command = trimText;
            }
            System.out.println("Class UserMessage, method definingCommandAndRest. Command is " + command + "\n");
        } else {
            int indexSplit = trimText.indexOf(' ');
            command = trimText.substring(0,indexSplit);
            String threePartText = trimText.substring(indexSplit + 1);
            if (command.equalsIgnoreCase("add")) {
                splitTextForAdd(threePartText);
                System.out.println("Class UserMessage, method definingCommandAndRest. Command is " + command +
                        ". Add`s data are " + threePartText + "\n");
            }
        }
    }


    //–азделение текста на три составл€ющие
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
