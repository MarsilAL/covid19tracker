package covid19tracker.infrastructure.web;
import covid19tracker.Log;
import covid19tracker.business.UserService;
import covid19tracker.infrastructure.db.SightingRepo;

import covid19tracker.domain.Sighting;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;


public class SightingEndpoint extends AbstractHandler {

    private final CorsHandler corsHandler;
    private final SightingRepo sightingRepo;
    private final UserService userService;
    private final Log my_log = new Log("log.txt");

    public SightingEndpoint(CorsHandler corsHandler, SightingRepo sightingRepo, UserService userService) throws IOException {
        this.corsHandler = corsHandler;
        this.sightingRepo = sightingRepo;
        this.userService = userService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        my_log.logger.info("processing sighting request ...");
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
        // TODO use correct header
        if (!this.validateUser(request.getHeader("which header??"))) {
            // TODO which status to send?
            response.setStatus(418);
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
        Double latitude;
        Double longitude;
        System.out.println(data.toString());
        // parse as json
        try {
            System.out.println("parsed data from request");
            JSONObject object = new JSONObject(data.toString());
            username = object.getString("username");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");
        } catch (JSONException ex) {
            System.err.println("missing field in json object or not parsable" + ex);
            my_log.logger.warning("parsable" + ex);
            ex.printStackTrace();
            response.setStatus(400);
            return;
        }

        if (username.isEmpty()) {
            my_log.logger.warning("invalid request, username must not be empty");
            response.setStatus(400);
            return;
        }

        Date instant = new Date();
        Sighting sighting = new Sighting(latitude, longitude, instant);

        if (username != null && latitude != null && longitude != null) {
            sightingRepo.saveTheSighting(username, sighting);
            response.setStatus(204);
        } else {
            response.setStatus(400);
            return;
        }
        // creation suceess!! tell the caller about the created user as json
        if (sighting != null) {

            my_log.logger.fine("SightingEndpoint working, save new data");

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("latitude", latitude);
            userJson.put("longitude", longitude);

            response.getWriter().print(userJson);
        } else {
            my_log.logger.warning("something is wrong");
        }
    }

    private boolean validateUser(String header) {
        // TODO quick check before doing a database query

        // TODO decode http header
        String decoded = new String(Base64.getDecoder().decode("SGFsbG86TWFyc2ls"));

        // TODO more magic regarding the http header

        String user = "foo";
        String password = "bar";

        return this.userService.validateUser(user, password);
    }

}