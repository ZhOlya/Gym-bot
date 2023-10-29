public interface TreatmentMessage {

    //Метод, котороый прверяет сообщателя на количество пробелов. Если их два, то возвращает true, если нет то возращает ??????7??
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
}
