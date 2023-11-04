package dat3.REST.routes;

import dat3.REST.exception.ExceptionController;
import dat3.REST.exception.NotFoundException;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private final ExceptionController exceptionController = new ExceptionController();
    private int count = 0;
    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);
    private final PlantRoutes plantRoutes = new PlantRoutes();

    private void requestInfoHandler(Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
        ctx.attribute("requestInfo", requestInfo);
    }

    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.before(this::requestInfoHandler);

            app.routes(() -> {
                // Add routes here. Example:
                path("/", plantRoutes.getRoutes());
                path("/", () -> {
                    get("/coffee", ctx -> ctx.status(418));
                });
            });

            // Simple logging of requests.
            app.after(ctx -> LOGGER.info(" Request {} - {} was handled with status code {}", count++, ctx.attribute("requestInfo"), ctx.status()));

            // Add exception handlers here.
            app.exception(IllegalArgumentException.class, exceptionController::illegalArgumentExceptionHandler);
            app.exception(NotFoundException.class, exceptionController::notFoundExceptionHandler);
            app.exception(Exception.class, exceptionController::exceptionHandler);

        };
    }
}
