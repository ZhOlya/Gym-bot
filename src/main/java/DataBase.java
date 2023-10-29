import java.sql.*;

public class DataBase implements TreatmentMessage{
    static String text = Bot.getMessageText();
    static String url = "jdbc:mysql://localhost:3306/gymbot?useUnicode=true&serverTimezone=UTC&useSSL=false";
    static String user = "root";
    static String pass = "root";

    
       


    public static void connectToSQL(){
        try{
            Connection connection = DriverManager.getConnection(url, user, pass);
//            Statement statement = connection.createStatement();

//            statement.executeUpdate("INSERT INTO exercises (nameExercies) VALUES ('LOX')");

            PreparedStatement statement = connection.prepareStatement("INSERT INTO exercises (nameExercies) VALUES (?)");
            statement.setString(1, text);
            statement.executeUpdate();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM exercises");
            while (resultSet.next()){
                String countTime = resultSet.getString("nameExercies");
                System.out.println(countTime);
            }
            connection.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }







}
