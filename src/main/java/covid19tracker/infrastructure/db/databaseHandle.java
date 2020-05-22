package covid19tracker.infrastructure.db;

import covid19tracker.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandle {
    String dbTable = System.getenv("DB_TABLE");
    private final Connection connection;

    public DatabaseHandle(Connection connection) {
        this.connection = connection;
    }

    public boolean saveToDb(User user) {
        return this.saveToDb(user.getUname(), user.getStatus(), user.getLatitude(), user.getLongitude(), user.getPswC());
    }

    public boolean saveToDb(String uname, boolean hasCovid, Double latitude, Double longitude, String pswC){


        try {
            String statusAsStr = hasCovid ? "true" : "false";

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "+dbTable+"(username, hascovid, latitude, longitude, pswC) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, statusAsStr);
            preparedStatement.setDouble(3, latitude);
            preparedStatement.setDouble(4, longitude);
            preparedStatement.setString(5, pswC);
            preparedStatement.executeUpdate();
            return true;

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean userExists(String username){
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

    public boolean userValid(String usernamen, String password) {
        // TODO implement me

        // is there a user with that combination?

        return false;
    }

}
