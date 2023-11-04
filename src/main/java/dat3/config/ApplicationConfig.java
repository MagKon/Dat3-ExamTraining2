package dat3.config;

import dat3.REST.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to create a Javalin instance.
 */
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    /**
     * Creates a Javalin instance with the given context path and default content type. This method is used to create a Javalin instance for the REST API.
     * @param config The JavalinConfig object that is used to configure the Javalin instance.
     */
    private static void configuration(JavalinConfig config) {
        config.routing.contextPath = "/api/v1"; // base path for all routes
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.plugins.register(new RouteOverviewPlugin("/routes")); // enables route overview at /
//        config.accessManager(ACCESS_MANAGER_HANDLER::accessManagerHandler); // Part of security. Referer to other projects for implementations.
    }

    /**
     * Starts the Javalin instance with the given port.
     * @param app The Javalin instance to start.
     * @param port The port to start the Javalin instance on.
     */
    public static void startServer(Javalin app, int port) {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        app.routes(routes.getRoutes(app));
        HibernateConfig.setTest(false);
        app.start(port);
    }

    /**
     * Stops the Javalin instance.
     * @param app The Javalin instance to stop.
     */
    public static void stopServer(Javalin app) {
        app.stop();
    }

    /**
     * Gets a property from the pom.xml file.
     * @param propName The name of the property to get.
     * @return The value of the property.
     * @throws IOException If the property could not be read from the pom.xml file.
     */
    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            LOGGER.error("Could not read property from pom file. Build Maven!");
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }
}
