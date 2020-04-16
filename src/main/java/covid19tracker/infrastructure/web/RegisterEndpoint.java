package covid19tracker.infrastructure.web;

import covid19tracker.business.RegisterService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import covid19tracker.domain.User;


public class RegisterEndpoint extends AbstractHandler {

    final private RegisterService registerService;

    public RegisterEndpoint(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        String username = "";
        String hasCovid = "";
        Double latitude = null;
        Double longitude = null;


        // get data from request
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
        }

        if (request.getParameter("hasCovid") != null) {
            hasCovid = request.getParameter("hasCovid");
        }


        if (request.getParameter("lat") != null) {
            latitude = Double.valueOf(request.getParameter("lat"));
        }

        if (request.getParameter("lng") != null) {
            longitude = Double.valueOf(request.getParameter("lng"));
        }

        System.out.println("request the Register Endpoint");
        System.out.format("The value of lat is: %f%n", latitude);
        System.out.format("The value of lng is: %f%n", longitude);
        System.out.println(username);

        // delegatye to service
        User user = registerService.registerUser(username, hasCovid, latitude, longitude);

        if (user != null) {
            JSONObject profile = new JSONObject();

            profile.put("UserName", username);
            profile.put("UserStatus", hasCovid);
            profile.put("UserLat", latitude);
            profile.put("UserLng", longitude);

            response.getWriter().print(profile);
            baseRequest.setHandled(true);
        } else {
            // todo
            // else retrun 400
            System.out.println("jjj falsch");

        }
    }

}
