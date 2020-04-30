package covid19tracker.infrastructure.db;

import covid19tracker.domain.Sighting;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SightingRepo {
    String dbTable = System.getenv("DB_TABLE");
    private Connection connection;

    public SightingRepo(Connection connection){

    }

    public  void saveTheSighting(String username, Sighting sighting){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userSighting(username, latitude, longitude, instant)" + "VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, sighting.getLatitude());
            preparedStatement.setDouble(3, sighting.getLongitude());
            preparedStatement.setDate(4, new java.sql.Date(sighting.getInstant().getTime()));

            preparedStatement.executeUpdate();

        }catch (Exception ex){
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());

            System.out.println(sighting.getInstant());
            System.out.println(sighting.getLatitude());
            System.out.println(sighting.getLongitude());
            System.out.println(username);
            System.out.println(dbTable);
            System.exit(0);
        }
    }
}
