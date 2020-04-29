package covid19tracker.infrastructure.web;


import covid19tracker.domain.Sighting;
import covid19tracker.domain.User;
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

    private final CorsHandler corsHandler;

    public SightingEndpoint(CorsHandler corsHandler){
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("processing register request ...");

        baseRequest.setHandled(true);
        corsHandler.handleCors(request, response);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
            return;
        }
/*
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(405);
            return;
        }
*/

/*
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
        // parse as json
        try {
            System.out.println("parsed data from request");
            JSONObject object = new JSONObject(data.toString());
            username = object.getString("username");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");
            System.out.println("- u  " + username);
            System.out.println("- X  " + latitude);
            System.out.println("- Y  " + longitude);
        } catch (JSONException ex) {
            System.out.println("missing field in json object or not parsable" + ex);
            response.setStatus(400);
            return;
        }
*/
        String username = null;
        Double latitude = null;
        Double longitude = null;

        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
        }
        if (request.getParameter("latitude") != null) {
            latitude = Double.parseDouble(request.getParameter("latitude"));
        }
        if (request.getParameter("longitude") != null) {
            longitude = Double.parseDouble(request.getParameter("longitude"));
        }


        if (username.isEmpty()) {
            System.out.println("invalid request, username must not be empty");
            response.setStatus(400);
            return;
        }
        Date date = new Date();

        System.out.println(date);

        System.out.println(username);
        System.out.println(latitude);
        System.out.println(longitude);

        Sighting sighting = new Sighting(latitude, longitude, date);
        // creation suceess!! tell the caller about the created user as json
        if (sighting != null) {

            System.out.println("created user !");

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);

            userJson.put("latitude", latitude);
            userJson.put("longitude", longitude);
            userJson.put("Date ", date);


            response.getWriter().print(userJson);
        } else {

            System.out.println(" falsch");

        }


    }
}