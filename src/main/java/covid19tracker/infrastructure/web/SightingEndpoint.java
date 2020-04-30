package covid19tracker.infrastructure.web;
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
import java.util.Date;


public class SightingEndpoint extends AbstractHandler {

    final private CorsHandler corsHandler;
    private final SightingRepo sightingRepo;

    public SightingEndpoint(CorsHandler corsHandler, SightingRepo sightingRepo) {
        this.corsHandler = corsHandler;
        this.sightingRepo = sightingRepo;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("processing sighting request ...");

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
/*
1
Insomnia installieren und post request mit json gegen sighting endpoint absetzen
2
insert statt update im repo



 */
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
            ex.printStackTrace();
            response.setStatus(400);
            return;
        }

        if (username.isEmpty()) {
            System.err.println("invalid request, username must not be empty");
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

            System.out.println("SightingEndpoint working ..");

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("latitude", latitude);
            userJson.put("longitude", longitude);

            response.getWriter().print(userJson);
        } else {

            System.out.println(" falsch");

        }


    }

}