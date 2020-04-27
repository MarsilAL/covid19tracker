/*
 * Covid19Tracker.
 */
package covid19tracker;

import covid19tracker.business.RegisterService;
import covid19tracker.infrastructure.db.PostgresClient;
import covid19tracker.infrastructure.db.databaseHandle;
import covid19tracker.infrastructure.web.Webserver;

import java.sql.Connection;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        
        String dbHost = System.getenv("DB_HOST");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        if (dbHost == null || dbUser == null || dbPass == null || dbName == null || dbHost.isBlank() || dbUser.isBlank()
                || dbPass.isBlank() || dbName.isBlank()) {
            System.err.println("missing environment variables");
            System.exit(1);
        } else {
            System.err.printf("Using host:%s, user:%s, pass:%s, dbName:%s\n ", dbHost, dbUser, dbPass, dbName);
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
        databaseHandle databaseHandle = new databaseHandle(connection);
        RegisterService usersService = new RegisterService(databaseHandle);

        Webserver webserver = new Webserver(usersService);
        webserver.startJetty();

    }
}
