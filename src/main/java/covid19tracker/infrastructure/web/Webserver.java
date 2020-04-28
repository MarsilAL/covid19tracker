package covid19tracker.infrastructure.web;


import covid19tracker.business.RegisterService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;


public class Webserver {


    private final RegisterService registerService;
    private final CorsHandler corsHandler;

    public Webserver(RegisterService registerService, CorsHandler corsHandler) {

        this.registerService = registerService;
        this.corsHandler = corsHandler;
    }

    public void startJetty() throws Exception {
        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");

        health.setAllowNullPathInfo(true);
        register.setAllowNullPathInfo(true);

        health.setHandler(new covid19tracker.infrastructure.web.HealthEndpoint());
        register.setHandler(new covid19tracker.infrastructure.web.RegisterEndpoint(registerService, corsHandler));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, register);

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