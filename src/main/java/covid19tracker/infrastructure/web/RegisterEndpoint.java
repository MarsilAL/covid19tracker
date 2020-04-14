package covid19tracker.infrastructure.web;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class RegisterEndpoint extends AbstractHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String uname = "";
    String hasCovid= "";
    String city = "";

    if(request.getParameter("uname") != null){
        uname = request.getParameter("uname");
    }

    if(request.getParameter("hasCovid") != null){
        hasCovid = request.getParameter("hasCovid");
    }


    if(request.getParameter("city") != null){
        city = request.getParameter("city");
    }

    baseRequest.setHandled(true);
    System.out.println("request the Register Endpoint");
    }

}
