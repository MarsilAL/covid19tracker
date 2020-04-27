package covid19tracker.infrastructure.web;

import covid19tracker.business.RegisterService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import covid19tracker.domain.User;


public class RegisterEndpoint extends AbstractHandler {

    final private RegisterService registerService;

    public RegisterEndpoint(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("processing request ... ");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (request.getRemoteHost().equals("null")) {
            response.setHeader("Access-Control-Allow-Origin", "null");
        }
        baseRequest.setHandled(true);


        /*

        to test the endpoint use curl ...
        curl --header "Content-Type: application/json"   --request POST   --data '{"username":"xyz","hasCovid":true, "latitude":0, "longitude":0}'   http://localhost:8080/register


        **/

        // onlz allow post requests
        if (!request.getMethod().equals("POST")) {
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
        boolean hasCovid;
        Double latitude;
        Double longitude;

        // parse as json
        try {
            System.out.println("parsed data from request");
            JSONObject object = new JSONObject(data.toString());
            username = object.getString("username");
            hasCovid = object.getBoolean("hasCovid");
            latitude = object.getDouble("latitude");
            longitude = object.getDouble("longitude");
        } catch (JSONException ex) {
            System.out.println("missing field in json object or not parsable" + ex);
            response.setStatus(400);
            return;
        }

        if (username.isEmpty()) {
            System.out.println("invalid request, username must not be empty");
            response.setStatus(400);
            return;
        }

        User user = registerService.registerUser(username, hasCovid, latitude, longitude);

        // creation suceess!! tell the caller about the created user as json
        if (user != null) {

            System.out.println("created user !");

            JSONObject userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("hasCovid", hasCovid);
            userJson.put("latitude", latitude);
            userJson.put("longitude", longitude);

            response.getWriter().print(userJson);
        } else {
            // todo
            // else retrun 400
            System.out.println("jjj falsch");

        }
    }

}
