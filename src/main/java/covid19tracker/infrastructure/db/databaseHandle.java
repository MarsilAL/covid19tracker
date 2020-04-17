package covid19tracker.infrastructure.db;

import covid19tracker.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class databaseHandle {
    String dbTable = System.getenv("DB_TABLE");
    private final Connection connection;

    public databaseHandle(Connection connection) {
        this.connection = connection;
    }

    public boolean saveToDb(User user) {
        return this.saveToDb(user.getUname(), user.getStatus(), user.getLatitude(), user.getLongitude());
    }

    public boolean saveToDb(String uname, String hasCovid, Double latitude, Double longitude){


        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "+dbTable+"(username, hascovid, latitude, longitude) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, hasCovid);
            preparedStatement.setDouble(3, latitude);
            preparedStatement.setDouble(4, longitude);
            preparedStatement.executeUpdate();
            return true;

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean chkUser(String username){
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM "+dbTable+" WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    return true;
                } else {
                    return false;
                }
            }

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }
}
