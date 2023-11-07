public class UserMessage {

    static String partOne = "";
    static String partTwo = "";
    static String parThree = "";

    public static String getPartOne() {
        return partOne;
    }

    public static String getPartTwo() {
        return partTwo;
    }

    public static String getParThree() {
        return parThree;
    }

    //Разделение текста на три составляющие
    public static void splitText(String text){

        int spaceIndexOne = text.indexOf(' ');
        int spaceIndexTwo = text.lastIndexOf(' ');
        partOne = text.substring(0, spaceIndexOne);
        partTwo = text.substring(spaceIndexOne + 1, spaceIndexTwo);
        parThree = text.substring(spaceIndexTwo + 1);
    }

    static boolean treatmentText (String text){
        int countSpace = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' '){
                countSpace++;
            }
        }
        if (countSpace == 2){
            return true;
        } else {
            return false;
        }
    }

//    //Метод, котороый прверяет сообщателя на количество пробелов. Если их два, то возвращает текст, если нет то возращает ??????7??
//    public static void treatmentText (String text){
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
