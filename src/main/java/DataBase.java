import org.jfree.chart.*;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


public class DataBase {

    private static final Logger loggerDataBase = LoggerFactory.getLogger(DataBase.class);
    static String text = "";

    static Properties properties = new Properties();
    static String url = "";
    static String user = "";
    static String pass = "";

    static Connection connection;
    static LocalDate date;

    private static ArrayList<String> showResultToUser;
    static EnumCommands enumCommands = null;
    static String command;

    public static void definitionOfCommand (){//Проверка на наличие определенной команды, если нет совпадения, то присваиваем команду дефолт
        text = Bot.getMessageText();
        UserMessage.definingCommandAndRest(text);
        command = UserMessage.getCommand().toUpperCase();

        try {
            enumCommands = EnumCommands.valueOf(command);
        } catch (IllegalArgumentException e){
            enumCommands = EnumCommands.DEFAULT;
        }
        System.out.println("Class DataBase, method connectToSQL. Command from user is " + command + "\n");

    }



    public static void connectToSQL(){
        //Считываение конфигурационного файла (ссылка, логин, пароль ДБ)
//        try {
//            properties.load(new FileInputStream("src/main/resources/database.properties"));
//        }catch (Exception e){
//            loggerDataBase.error("The error to read config file");
//        }

        try {
            InputStream input = DataBase.class.getClassLoader().getResourceAsStream("database.properties");

            if (input == null){
                loggerDataBase.error("Unable to find database.properties");
                return;
            }
            properties.load(input);
        }catch (IOException e){
            loggerDataBase.error("Error occurred while reading config file", e);
        }

        url = properties.getProperty("url");
        user = properties.getProperty("user");
        pass = properties.getProperty("pass");

        definitionOfCommand();

        if (command.equals("INFO")){
            getInfo();
        } else {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver"); // Загружаем JDBC драйвер
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("I AM IN DATABASE");
                switch (enumCommands){
                    case ADD -> addDataInDB();
                    case WEEK -> showResultLastWeek();
                    case MONTH -> showResultLastMonth();
                    case HALFYEAR -> showResultLastHalfYear();
                    case GRAPH -> sendGraph();
                    default -> Bot.response.setText("Sorry, I don`t understand you.");
                }
                connection.close();
            } catch(ClassNotFoundException  | SQLException e) {
                loggerDataBase.error("Error occurred in Database class: ", e);
            }
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
            StringBuilder messageToUser = new StringBuilder();
            while (resultSet.next()){
                String name = resultSet.getString("nameExercise");
                String countTime = resultSet.getString("countTime");
                String countApproaches = resultSet.getString("countApproaches");
                String date = resultSet.getString("date");
                messageToUser.append("Exercise is added. " + "\n" + name + ". Number of times - " + countTime +
                        ". Number of approaches - " + countApproaches + ". Date " + date + "\n");
                System.out.println("Class DataBase, addDataDB. " + name +" " + countTime + " " + countApproaches);
            }
            Bot.response.setText(messageToUser.toString());
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
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT nameExercise, SUM(countTime) AS totalTime, SUM(countApproaches) AS totalAP FROM exercises WHERE idChat = ? AND date BETWEEN ? AND ? GROUP BY nameExercise");
            statement.setString(1, idChat);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder messageToUser = new StringBuilder(); // Создаем сообщение для юзера, куда добавляем результаты
            while (resultSet.next()){

                String name = resultSet.getString("nameExercise");
                String countTime = resultSet.getString("totalTime");
                String countApproaches = resultSet.getString("totalAp");
//                String date = resultSet.getString("date");
                messageToUser.append(name + ". Times - " + countTime +
                        ". App-s - " + countApproaches + "\n" );
                System.out.println("Class DataBase, method getPastResult. " + name +" " + countTime + " " + countApproaches + " " + "\n");
            }
            Bot.response.setText(messageToUser.toString());

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getInfo (){
        String messageInfo = """
                Use next commands:
                1. info
                This command shows you information about all commands
                
                2. add name Exercises (in one word) number of times number of approaches
                (example: add jump 100 3)
                         
                3. week
                This command shows your statistic of last week
                                
                4. month
                This command shows your statistic of last month
                                
                5. halfyear
                This command shows your statistic of last 6 months
                                
                6. graph
                This command shows your activity as a graph for the last month""";
        Bot.response.setText(messageInfo);
    }

    //Создание и отправка графика
    public static void sendGraph(){
        String idChat = String.valueOf(Bot.getIdChat());
        getStatisticsGraph(idChat);
        new Bot().sendGraph(idChat);
    }

    //непостедственно создание графика
    public static void getStatisticsGraph (String idChat) {
        date = LocalDate.now();
        String endDate = String.valueOf(date);
        String startDate = String.valueOf(date.minusMonths(1));

        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT nameExercise, countTime*countApproaches AS quantity, date FROM exercises WHERE idChat = ? AND date BETWEEN ? AND ? ORDER BY date");
            statement.setString(1, idChat);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            TimeSeries series;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

             while (resultSet.next()){
                 String nameExercise = resultSet.getString("nameExercise");
                 int quantity = resultSet.getInt("quantity");
                 Date date = dateFormat.parse(resultSet.getString("date"));

                 //Проверка существоания серии для этого упражнения
                 series = dataset.getSeries(nameExercise);
                 if (series == null){//Если она не существует, то создаем новую
                     series = new TimeSeries(nameExercise);
                     dataset.addSeries(series);
                     System.out.println("Make new series: " + nameExercise);//вывод в консоль, что мы создали новую серию
                 }
                 //Добавляем данные в серию
                 series.addOrUpdate(new Day(date), quantity);
                 System.out.println("Added to series '" + nameExercise + "': " + date + ", " + quantity);//вывод в консоль доавляемых данных
             }

             //вывод в консоль общей информации для графика
            for (int i = 0; i < dataset.getSeriesCount(); i++) {
                series = dataset.getSeries(i);
                System.out.println("Total data points for series '" + series.getKey() + "': " + series.getItemCount());
            }

             //Создание графика
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Statistics of your exercises", //Заголовок
                    "Date",// Ось х
                    "Quantity", // Ось у
                    dataset, // данные
                    true, true,false);


             // Настройка оси Х для отображения дат и установка толщины линий
            XYPlot plot = (XYPlot) chart.getPlot();
            XYItemRenderer renderer = plot.getRenderer();

            float lineWidth = 3.0f; // Устанавливаем более толстый BasicStroke для серии
            BasicStroke stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

            //Применяем настройки ко всем сериям на графике
            int seriesCount = plot.getSeriesCount();
            for (int i = 0; i < seriesCount; i++) {
                renderer.setSeriesStroke(i, stroke);

            }

            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

            //Сохранение графика в файл
            ChartUtilities.saveChartAsPNG(new File("C:\\OlgaZh\\01 Study\\Gym-bot\\graph\\Exercises_Chart.png"), chart, 1800, 1200);

        } catch (SQLException e){
            loggerDataBase.error("Error occurred in DataBase class (make graph)", e);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}



