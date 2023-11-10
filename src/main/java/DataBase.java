import java.sql.*;
import java.time.LocalDate;

public class DataBase {
    static String text = "";
    static String url = "jdbc:mysql://localhost:3306/gymbot?useUnicode=true&serverTimezone=UTC&useSSL=false";
    static String user = "root";
    static String pass = "root";

    static Connection connection;
    static LocalDate date;


    public static void connectToSQL(){
        text = Bot.getMessageText();
        UserMessage.definingCommandAndRest(text);
        String command = UserMessage.getCommand();
        try{
            connection = DriverManager.getConnection(url, user, pass);
            switch (command){
                case "add":
                    addDataInDB();
                    break;
                case "week":
                    showResultLastWeek();
                    break;
                case "month":
                    showResultLastMonth();
                    break;
                case "halfyear":
                    showResultLastHalfYear();
                    break;
                default:
                    System.out.println("Wrong message!");
            }

//            showResultLastWeek();
            connection.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }



    public static void addDataInDB() throws SQLException {
        System.out.println("Class DataBase. method addData in start " + Bot.getMessageText());
        date = LocalDate.now();

            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO exercises (nameExercise, countTime, countApproaches, date, idChat) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, UserMessage.getPartOne());
            statement.setString(2, UserMessage.getPartTwo());
            statement.setString(3, UserMessage.getParThree());
            statement.setString(4, String.valueOf(date));
            statement.setString(5, String.valueOf(Bot.getIdChat()));
            statement.executeUpdate();
            //UserMessage.clearPartsOfText();
            //Вывод в консоль БД
            ResultSet resultSet = statement.executeQuery("SELECT * FROM exercises ORDER BY id DESC LIMIT 10");
            while (resultSet.next()){
                String name = resultSet.getString("nameExercise");
                String countTime = resultSet.getString("countTime");
                System.out.println("Class DataBase, addDataDB. " + name +" " + countTime);
            }

    }

    public static void showResultLastWeek(){
        date = LocalDate.now();
        LocalDate firstDate = date.minusDays(7); //Минус 7 дней от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay);
        System.out.println("This is from class DataBase, method showResultLastWeek ");

    }

    public static void showResultLastMonth(){
        date = LocalDate.now();
        LocalDate firstDate = date.minusMonths(1); //Минус 1 месяц от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay);
        System.out.println("This is from class DataBase, method showResultLastMonth ");
    }

    public static void showResultLastHalfYear(){
        date = LocalDate.now();
        LocalDate firstDate = date.minusMonths(6); //Минус 6 месяцев от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay);
        System.out.println("This is from class DataBase, method showResultLastHalfYear ");
    }

    public static void getPastResult(String startDate, String endDate){
        String idChat = "182224604";
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM exercises WHERE idChat = ? AND date BETWEEN ? AND ?");
            statement.setString(1, idChat);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("nameExercise");
                String countTime = resultSet.getString("countTime");
                String countApproaches = resultSet.getString("countApproaches");
                String date = resultSet.getString("date");
                System.out.println("Class DataBase, method getPastResult. " + name +" " + countTime + " " + countApproaches + " " + date + "\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }







}



