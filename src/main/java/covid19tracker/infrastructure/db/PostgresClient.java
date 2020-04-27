package covid19tracker.infrastructure.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresClient {

    public static Connection connect(String host, String user, String pwd, String db) {
        Connection c = null;
        String dbPort = System.getenv("DB_PORT");
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager.getConnection("jdbc:postgresql://"+host+":"+dbPort+"/" + db,user, pwd);

        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           return c;

        }
        System.out.println("Opened database successfully");
        return c;
     }
}