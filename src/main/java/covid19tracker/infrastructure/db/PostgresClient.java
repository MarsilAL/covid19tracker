package covid19tracker.infrastracture.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresClient {

    public static Connection connect(String host, String user, String pwd, String db) {
        Connection c = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager.getConnection("jdbc:postgresql://"+host+":5432/" + db,user, pwd);

        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           return c;

        }
        System.out.println("Opened database successfully");
        return c;
     }
}