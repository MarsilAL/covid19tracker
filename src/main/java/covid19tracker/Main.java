/*
 * Covid19Tracker.
 */
package covid19tracker;

import covid19tracker.infrastracture.db.PostgresClient;
import covid19tracker.infrastructure.web.Webserver;

import java.sql.Connection;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        
        String dbHost = "localhost";
        String dbUser = "postgres";
        String dbPass = "ideas";
        String dbName = "trackerdb";

        if (dbHost == null || dbUser == null || dbPass == null || dbName == null || dbHost.isBlank() || dbUser.isBlank()
                || dbPass.isBlank() || dbName.isBlank()) {
            System.err.println("missing environment variables");
            System.exit(1);
        } else {
            System.err.printf("Using host:%s, user:%s, pass:%s, dbName:%s\n ", dbHost, dbUser, "The Pass", dbName);
        }

        // connect to db
        Connection connection = PostgresClient.connect(dbHost, dbUser, dbPass, dbName);
        ;
        if (connection == null) {
            System.err.println("failed to connect to Database");
            System.exit(1);
        } else {
            System.out.println("connected:" + new Date());
        }


        Webserver webserver = new Webserver(connection);
        webserver.startJetty();

    }
}
