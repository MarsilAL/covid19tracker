package covid19tracker.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hashing {
    private final byte[] salt = new byte[]{
            -123, 39, -113, 105, 28, 93, 73, -95, 58, -59, 106, -63, 116, 46, -110, -80
    };
    public String hasher(String passwordToHash) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        return Arrays.toString(md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8)));
    }
}
