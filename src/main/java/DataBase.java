import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataBase {
    static String text = "";
    static String url = "jdbc:mysql://localhost:3306/gymbot?useUnicode=true&serverTimezone=UTC&useSSL=false";
    static String user = "root";
    static String pass = "root";

    static Connection connection;
    static LocalDate date;

    private static ArrayList<String> showResultToUser;


    public static void connectToSQL(){
        text = Bot.getMessageText();
        UserMessage.definingCommandAndRest(text);
        String command = UserMessage.getCommand().toUpperCase();
        EnumCommands enumCommands = EnumCommands.valueOf(command);
        System.out.println("Class DataBase, method connectToSQL. Command from user is " + command + "\n");
        try{
            connection = DriverManager.getConnection(url, user, pass);
            switch (enumCommands){
                case ADD -> addDataInDB();
                case WEEK -> showResultLastWeek();
                case MONTH -> showResultLastMonth();
                case HALFYEAR -> showResultLastHalfYear();
                case INFO -> getInfo();
                default -> System.out.println("Error command");
            }
            connection.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }



    public static void addDataInDB() throws SQLException {
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

            ResultSet resultSet = statement.executeQuery("SELECT * FROM exercises ORDER BY id DESC LIMIT 1 ");
            StringBuilder messafeToUser = new StringBuilder();
            while (resultSet.next()){
                String name = resultSet.getString("nameExercise");
                String countTime = resultSet.getString("countTime");
                String countApproaches = resultSet.getString("countApproaches");
                String date = resultSet.getString("date");
                messafeToUser.append("Exercise is added. " + "\n" + name + ". Number of times - " + countTime +
                        ". Number of approaches - " + countApproaches + ". Date " + date + "\n");
                System.out.println("Class DataBase, addDataDB. " + name +" " + countTime);
            }
            Bot.response.setText(messafeToUser.toString());
    }

    public static void showResultLastWeek(){
        String chatId = String.valueOf(Bot.getIdChat());
        date = LocalDate.now();
        LocalDate firstDate = date.minusDays(7); //Минус 7 дней от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay, chatId);
        System.out.println("This is from class DataBase, method showResultLastWeek " + "\n");

    }

    public static void showResultLastMonth(){
        String chatId = String.valueOf(Bot.getIdChat());
        date = LocalDate.now();
        LocalDate firstDate = date.minusMonths(1); //Минус 1 месяц от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay, chatId);
        System.out.println("This is from class DataBase, method showResultLastMonth " + "\n");
    }

    public static void showResultLastHalfYear(){
        String chatId = String.valueOf(Bot.getIdChat());
        date = LocalDate.now();
        LocalDate firstDate = date.minusMonths(6); //Минус 6 месяцев от сегоднешней даты
        // Преобразование дат в строки
        String endDay = String.valueOf(date);
        String startDay = String.valueOf(firstDate);
        getPastResult(startDay, endDay, chatId);
        System.out.println("This is from class DataBase, method showResultLastHalfYear " + "\n");
    }

    public static void getPastResult(String startDate, String endDate, String idChat){
        try {
//            PreparedStatement statement = connection.prepareStatement
//                    ("SELECT * FROM exercises WHERE idChat = ? AND date BETWEEN ? AND ?");
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT nameExercise, SUM(countTime) AS totalTime, SUM(countApproaches) AS totalAP FROM exercises WHERE idChat = ? AND date BETWEEN ? AND ? GROUP BY nameExercise");
            statement.setString(1, idChat);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder messageToUser = new StringBuilder(); // Создаем сообщение для юзера, куда добавляем результаты
            while (resultSet.next()){

                String name = resultSet.getString("nameExercise").toLowerCase();
                String countTime = resultSet.getString("totalTime");
                String countApproaches = resultSet.getString("totalAp");
//                String date = resultSet.getString("date");
                messageToUser.append(name + ". Times - " + countTime +
                        ". App-s - " + countApproaches + "\n" + "\n");
                System.out.println("Class DataBase, method getPastResult. " + name +" " + countTime + " " + countApproaches + " " + "\n");
            }
            Bot.response.setText(messageToUser.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getInfo (){
        StringBuilder messageInfo = new StringBuilder();
        messageInfo.append("""
                Use next commands:
                1. add name Exercises (in one word) number of times number of approaches
                (example: add jump 100 3)
                
                2. week
                This command show your statistic of last week
                
                3. month
                This command show your statistic of last month
                
                4. halfyear
                This command show your statistic of last 6 months
                
                5. info
                This command show you information about all commands""");
        Bot.response.setText(messageInfo.toString());
    }







}



