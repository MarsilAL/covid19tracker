package covid19tracker.business;

import covid19tracker.domain.User;
import covid19tracker.infrastructure.db.DatabaseHandle;

import java.security.NoSuchAlgorithmException;

public class UserService {
    private final DatabaseHandle databaseHandle;

    public UserService(DatabaseHandle databaseHandle) {
        this.databaseHandle = databaseHandle;
    }

    public User registerUser(String username, boolean hasCovid, Double latitude, Double longitude, String pswC) throws NoSuchAlgorithmException {
        //  User user = new User (username, hasCovid, latitude, longitude);
        if (databaseHandle.userExists(username)) {
            System.out.println("try another username " + username);
            return null;
        }
        if (!databaseHandle.saveToDb(username, hasCovid, latitude, longitude, pswC)) {
            return null;
        }
        return new User(username, hasCovid, latitude, longitude, pswC);

    }

    public boolean validateUser(String username, String password) {
        System.out.println("userService : " +username+";"+password);
        return databaseHandle.userValid(username, password);
    }


}

