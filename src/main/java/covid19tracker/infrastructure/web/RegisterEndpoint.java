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
import java.security.NoSuchAlgorithmException;

import covid19tracker.domain.User;

public class RegisterEndpoint extends AbstractHandler {

    final private UserService userService;
    final private CorsHandler corsHandler;

    public RegisterEndpoint(UserService userService, CorsHandler corsHandler) {
        this.userService = userService;
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log my_log = new Log("log.txt");
        my_log.logger.info("processing register request ...");

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


        // get data from request
        StringBuilder data = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        String username;
        String pswC; // used for http basic auth
        boolean hasCovid;
        Double latitude;
        Double longitude;

        // parse as json
        try {
            my_log.logger.info("parsed data from request");
            JSONObject object = new JSONObject(data.toString());
            username = object.getString("username");
            hasCovid = object.getBoolean("hasCovid");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");
            pswC = object.getString("pswC");

        } catch (JSONException ex) {
            my_log.logger.warning("missing field in json object or not parsable" + ex);
            response.setStatus(400);
            return;
        }

        if (username.isEmpty()) {
            my_log.logger.warning("invalid request, username must not be empty");
            response.setStatus(400);
            return;
        }
        if (pswC.isEmpty()) {
            System.out.println("invalid request, password must not be empty");
            response.setStatus(400);
            return;
        }

        User user = null;
        try {
            user = userService.registerUser(username, hasCovid, latitude, longitude, pswC);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // creation suceess!! tell the caller about the created user as json
        if (user != null) {

            System.out.println("SightingEndpoint working ..");

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("hasCovid", hasCovid);
            userJson.put("latitude", latitude);
            userJson.put("longitude", longitude);
            userJson.put("paswoerd", pswC);

            response.getWriter().print(userJson);
        } else {
            // todo
            // else retrun 400
            System.out.println(" falsch");

        }


    }
}
