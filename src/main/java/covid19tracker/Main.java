/*
 * Covid19Tracker.
 */
package covid19tracker;

import covid19tracker.business.UserService;
import covid19tracker.infrastructure.Hashing;
import covid19tracker.infrastructure.db.PostgresClient;
import covid19tracker.infrastructure.db.DatabaseHandle;
import covid19tracker.infrastructure.web.CorsHandler;
import covid19tracker.infrastructure.web.Webserver;
import covid19tracker.infrastructure.db.SightingRepo;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {

        Log my_log = new Log("log.txt");

        String dbHost = System.getenv("DB_HOST");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        if (dbHost == null || dbUser == null || dbPass == null || dbName == null || dbHost.isBlank() || dbUser.isBlank()
                || dbPass.isBlank() || dbName.isBlank()) {
            my_log.logger.config("missing environment variables");
            System.exit(1);
        } else {
            System.err.printf("Using host:%s, user:%s, pass:%s, dbName:%s\n ", dbHost, dbUser, dbPass, dbName);
        }

        // connect to db
        Connection connection = PostgresClient.connect(dbHost, dbUser, dbPass, dbName);
        Hashing hashing = new Hashing();
        ;
        if (connection == null) {
            my_log.logger.config("failed to connect to Database");

            System.exit(1);
        } else {
            my_log.logger.info("connected");
        }
        DatabaseHandle databaseHandle = new DatabaseHandle(connection, hashing);
        UserService userService = new UserService(databaseHandle);
        CorsHandler corsHandler = new CorsHandler();
        SightingRepo sightingRepo = new SightingRepo(connection);

        Webserver webserver = new Webserver(userService, corsHandler, sightingRepo);
        webserver.startJetty();

    }
}
