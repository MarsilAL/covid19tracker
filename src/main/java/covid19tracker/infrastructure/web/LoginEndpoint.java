package covid19tracker.infrastructure.web;
import covid19tracker.Log;
import covid19tracker.business.UserService;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;



public class LoginEndpoint extends AbstractHandler {

    private final CorsHandler corsHandler;
    private final UserService userService;
    private final Log my_log = new Log("log.txt");


    public LoginEndpoint(CorsHandler corsHandler, UserService userService) throws IOException {
        this.corsHandler = corsHandler;
        this.userService = userService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        my_log.logger.info("processing login request ...");
        baseRequest.setHandled(true);
        corsHandler.handleCors(request, response);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
            return;
        }

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(405);
            return;
        }

        // validate user
        boolean valid = this.validateUser(request.getHeader("Authorization"));
        if (valid) {
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

    private boolean validateUser(String header) {
        if (header == null || !header.startsWith("Basic ")) {
            return false;
        }

        // sample "Basic c3RlZmFuOnN0ZWZhbg=="
        String coded_user_password = header.split(" ")[1];

        String decoded = new String(Base64.getDecoder().decode(coded_user_password));

        String usernameC = decoded.split(":")[0];
        String passwordC = decoded.split(":")[1];

        System.out.println("decoded_username:  " + usernameC + " decoded_pass:  " + passwordC);

        return this.userService.validateUser(usernameC, passwordC);
    }

}