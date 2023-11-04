package dat3.REST.exception;

import dat3.REST.routes.Routes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExceptionController {
    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    public void exceptionHandler(Exception e, Context ctx) {
        LOGGER.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage(), getTimestamp()));
    }

    public void illegalArgumentExceptionHandler(IllegalArgumentException e, Context context) {
        LOGGER.error(context.attribute("requestInfo") + " " + context.res().getStatus() + " " + e.getMessage());
        context.status(400);
        context.json(new Message(400, e.getMessage(), getTimestamp()));
    }

    public void notFoundExceptionHandler(NotFoundException e, Context context) {
        LOGGER.error(context.attribute("requestInfo") + " " + context.res().getStatus() + " " + e.getMessage());
        context.status(404);
        context.json(new Message(404, e.getMessage(), getTimestamp()));
    }

    private String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();

        // Define the pattern for formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Use uppercase 'HH' for 24-hour format

        return now.format(formatter);
    }
}
