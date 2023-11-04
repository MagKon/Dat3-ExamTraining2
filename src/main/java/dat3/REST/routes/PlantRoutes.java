package dat3.REST.routes;

import dat3.REST.controller.PlantController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private final PlantController plantController = new PlantController();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/plants", () -> {
                get("", plantController.getAll());
                get("/{id}", plantController.getById());
                post("", plantController.create());

                path("/type", () -> {
                    get("/{type}", plantController.getByType());
                });
            });
        };
    }
}
