public interface TreatmentMessage {

    //�����, �������� �������� ��������� �� ���������� ��������. ���� �� ���, �� ���������� true, ���� ��� �� ��������� false
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
