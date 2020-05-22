package covid19tracker.infrastructure.db;

import covid19tracker.domain.User;
import covid19tracker.infrastructure.Hashing;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandle {
    String dbTable = System.getenv("DB_TABLE");
    private final Connection connection;
    private final Hashing hashing;

    public DatabaseHandle(Connection connection, Hashing hashing) {
        this.connection = connection;
        this.hashing = hashing;
    }

    public boolean saveToDb(User user) throws NoSuchAlgorithmException {
        return this.saveToDb(user.getUname(), user.getStatus(), user.getLatitude(), user.getLongitude(), user.getPswC());
    }

    public boolean saveToDb(String uname, boolean hasCovid, Double latitude, Double longitude, String pswC) throws NoSuchAlgorithmException {


        try {
            String hashedPassword = hashing.hasher(pswC);
            System.out.println("hashed pass:  "+hashedPassword);

            String statusAsStr = hasCovid ? "true" : "false";

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "+dbTable+"(username, hascovid, latitude, longitude, pswC) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, statusAsStr);
            preparedStatement.setDouble(3, latitude);
            preparedStatement.setDouble(4, longitude);
            preparedStatement.setString(5, hashedPassword);
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


        try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM accounts WHERE username = ? AND pswc = ?");
                preparedStatement.setString(1, usernamen);
                preparedStatement.setString(2, password);
                System.out.println("U:  "+usernamen+ "&" + " P: " +password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    if(resultSet.getInt(1) == 1){
                        System.out.println("db if 1 result   "+resultSet.getInt(1));
                        return true;
                    } else {
                        System.out.println("db else if 0 result   "+resultSet.getInt(1));
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
