import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class TcpConnectionPoolFactory  {
    private static final String user = "rootOlga";
    private static final String pass = "653358";
    private static final String nameDB = "gymbot";

    private static final String host = "34.29.170.206";
    private static final String port = "3306";

    public static DataSource createConnectingPool(){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", host, port, nameDB));
        config.setUsername(user);
        config.setPassword(pass);

        return new HikariDataSource(config);
    }
}
