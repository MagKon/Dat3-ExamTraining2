package dat3.REST.controller;

import dat3.PopulateData;
import dat3.config.ApplicationConfig;
import dat3.config.HibernateConfig;
import dat3.config.boilerplate.DAO;
import dat3.persistence.DAO.IPlantDAO;
import dat3.persistence.DAO.PlantDAO;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import dat3.persistence.Reseller_Plant;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class PlantControllerTest {
    static EntityManagerFactory entityManagerFactory;
    static IPlantDAO plantDAO;
    static DAO<Reseller> resellerDAO;
    static DAO<Reseller_Plant> reseller_plantDAO;

    static Javalin app;
    static String base_url = "http://localhost:7777/api/v1";

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        entityManagerFactory = HibernateConfig.getEntityManagerFactory();

        plantDAO = new PlantDAO(Plant.class, entityManagerFactory);
        resellerDAO = new DAO<>(Reseller.class, entityManagerFactory);
        reseller_plantDAO = new DAO<>(Reseller_Plant.class, entityManagerFactory);

        // Start server
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp() {
        PopulateData.populate(entityManagerFactory.createEntityManager());
    }

    @AfterEach
    void tearDown() {
        plantDAO.truncate();
        reseller_plantDAO.truncate();
        resellerDAO.truncate();
    }

    @Test
    void testGetAll() {
        given()
                .contentType("application/json")
                .when()
                .get(base_url + "/plants")
                .then()
                .assertThat()
                .statusCode(200)
                .body("plants", org.hamcrest.Matchers.hasSize(3));
    }

    @Test
    void testGetById() {
        given()
                .contentType("application/json")
                .when()
                .get(base_url + "/plants/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void testGetByType() {
        given()
                .contentType("application/json")
                .when()
                .get(base_url + "/plants/type/Tree")
                .then()
                .assertThat()
                .statusCode(200)
                .body("plants", org.hamcrest.Matchers.hasSize(1));
    }

    @Test
    void testCreate() {
        given()
                .contentType("application/json")
                .body("{\"plantName\": \"TestPlant\", \"plantType\": \"TestType\", \"price\": 100, \"height\": 0.5}")
                .when()
                .post(base_url + "/plants")
                .then()
                .assertThat()
                .statusCode(201)
                .body("plantName", equalTo("TestPlant"));
    }
}