import java.sql.*;

public class DataBase implements TreatmentMessage{
    //static String text = Bot.getMessageText();
    static String url = "jdbc:mysql://localhost:3306/gymbot?useUnicode=true&serverTimezone=UTC&useSSL=false";
    static String user = "root";
    static String pass = "root";
    static String text = "NO";



    public static void connectToSQL(){
        try{
            Connection connection = DriverManager.getConnection(url, user, pass);
//            Statement statement = connection.createStatement();

//            statement.executeUpdate("INSERT INTO exercises (nameExercies) VALUES ('LOX')");
            if (UserMessage.treatmentText(text) == true){
                UserMessage.splitText(text);
                PreparedStatement statement = connection.prepareStatement
                        ("INSERT INTO exercises (nameExercies, countTime, countApproaches) VALUES (?,?,?)");
                statement.setString(1, UserMessage.getPartOne());
                statement.setString(2, UserMessage.getPartTwo());
                statement.setString(3, UserMessage.getParThree());
                statement.executeUpdate();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM exercises");
                while (resultSet.next()){
                    String countTime = resultSet.getString("nameExercies");
                    System.out.println(countTime);
                }
            } else {
                System.out.println("Oh no, something wrong");
            }
            connection.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }







}
