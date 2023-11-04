package dat3.REST.controller;

import dat3.REST.exception.NotFoundException;
import dat3.config.HibernateConfig;
import dat3.persistence.DAO.PlantDAO;
import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import io.javalin.http.Handler;

public class PlantController implements IController {
    private final PlantDAO plantDAO = new PlantDAO(Plant.class, HibernateConfig.getEntityManagerFactory());
    @Override
    public Handler getAll() {
        return ctx -> { // Consider converting plants to DTOs
            ctx.json(plantDAO.getAll());
        };
    }

    @Override
    public Handler getById() {
        return ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).check(PlantDAO::validatePrimaryKey, "Not a valid id").get();
            if (plantDAO.findById(id) == null) throw new NotFoundException("Plant not found");
            ctx.json(plantDAO.findById(id));
        };
    }

    public Handler getByType() {
        return ctx -> {
            String type = ctx.pathParam("type");
            if (!PlantDAO.validatePlantType(type)) throw new IllegalArgumentException("Invalid plant type: " + type);
            if (plantDAO.getByType(type).isEmpty()) throw new NotFoundException("No plants of type " + type + " found");
            ctx.json(plantDAO.getByType(type));
        };
    }

    @Override
    public Handler create() {
        return ctx -> {
            PlantDto plant = ctx.bodyAsClass(PlantDto.class);
            if (!PlantDAO.validateDto(plant)) throw new IllegalArgumentException("Invalid plant");

            plant = PlantDAO.convertToDto(plantDAO.add(plant));

            ctx.status(201);
            ctx.json(plant);
        };
    }

    @Override
    public Handler update() {
        return create();
    }

    @Override
    public Handler delete() {
        return ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).check(PlantDAO::validatePrimaryKey, "Not a valid id").get();

            Plant plant = plantDAO.findById(id);
            if (plant == null) throw new NotFoundException("Plant not found");

            plantDAO.delete(plant);

            ctx.status(204);
        };
    }
}
