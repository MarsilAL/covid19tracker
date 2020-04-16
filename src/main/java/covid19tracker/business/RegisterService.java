package covid19tracker.business;

import covid19tracker.domain.User;
import covid19tracker.infrastructure.db.databaseHandle;

public class RegisterService {
    final private databaseHandle databaseHandle;

    public RegisterService(databaseHandle databaseHandle) {
        this.databaseHandle = databaseHandle;
    }

    public User registerUser(String username, String hasCovid, Double latitude, Double longitude) {
        //  User user = new User (username, hasCovid, latitude, longitude);
        while (databaseHandle.chkUser(username)) {
            System.out.println("try another username" + username);
        }
        if (!databaseHandle.saveToDb(username, hasCovid, latitude, longitude)) {
            return null;
        }
        return new User(username, hasCovid, latitude, longitude);

    }
}

