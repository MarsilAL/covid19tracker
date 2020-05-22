package covid19tracker.infrastructure.web;


import covid19tracker.business.UserService;
import covid19tracker.infrastructure.db.SightingRepo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;


public class Webserver {


    private final UserService userService;
    private final CorsHandler corsHandler;
    private final SightingRepo sightingRepo;

    public Webserver(UserService userService, CorsHandler corsHandler, SightingRepo sightingRepo) {

        this.userService = userService;
        this.corsHandler = corsHandler;
        this.sightingRepo = sightingRepo;
    }

    public void startJetty() throws Exception {
        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");
        final ContextHandler sighting = new ContextHandler("/sighting");

        health.setAllowNullPathInfo(true);
        register.setAllowNullPathInfo(true);
        sighting.setAllowNullPathInfo(true);

        health.setHandler(new covid19tracker.infrastructure.web.HealthEndpoint());
        register.setHandler(new covid19tracker.infrastructure.web.RegisterEndpoint(userService, corsHandler));

        sighting.setHandler(new covid19tracker.infrastructure.web.SightingEndpoint(corsHandler, sightingRepo));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, register, sighting);

        String port = System.getenv("PORT");
        if (port == null) {
            port = "8080";
        }
        System.out.println("PORT: " + port);
        final Server server = new Server(Integer.parseInt(port));

        server.setHandler(contexts);
        server.start();
        server.join();
    }
}