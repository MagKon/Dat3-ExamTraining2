package dat3.persistence.DAO;

import dat3.PopulateData;
import dat3.config.HibernateConfig;
import dat3.config.boilerplate.DAO;
import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import dat3.persistence.Reseller_Plant;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantDAOTest {
    public static EntityManagerFactory entityManagerFactory;
    public static IPlantDAO plantDAO;
    public static DAO<Reseller> resellerDAO;
    public static DAO<Reseller_Plant> reseller_plantDAO;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        entityManagerFactory = HibernateConfig.getEntityManagerFactory();

        plantDAO = new PlantDAO(Plant.class, entityManagerFactory);
        resellerDAO = new DAO<>(Reseller.class, entityManagerFactory);
        reseller_plantDAO = new DAO<>(Reseller_Plant.class, entityManagerFactory);
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
    void testGetPlant() {
        Plant plant = plantDAO.findById(1);
        assertEquals("Bonsai", plant.getPlantName());
    }

    @Test
    void testGetAllPlants() {
        assertEquals(3, plantDAO.getAll().size());
    }

    @Test
    void testCreatePlant() {
        Plant plant = new Plant();
        plant.setPlantName("TestPlant");
        plant.setPlantType("TestType");
        plant.setPrice(100);
        plant.setHeight(100);
        plantDAO.save(plant);
        assertEquals(4, plantDAO.getAll().size());
    }

    @Test
    void testUpdatePlant() {
        Plant plant = plantDAO.findById(1);
        plant.setPlantName("TestPlant");
        plantDAO.merge(plant);
        assertEquals("TestPlant", plantDAO.findById(1).getPlantName());
    }

    @Test
    void testDeletePlant() {
        Plant plant = plantDAO.findById(1);
        plantDAO.delete(plant);
        assertEquals(2, plantDAO.getAll().size());
    }

    @Test
    void testGetByType() {
        assertEquals(1, plantDAO.getByType("Cactus").size());
    }

    @Test
    void testAddPlant() {
        PlantDto plantDto = new PlantDto(0, "TestPlant", "TestType", 100, 100);
        plantDAO.add(plantDto);
        assertEquals(4, plantDAO.getAll().size());
    }

    @Test
    void testAddPlantToReseller() {
        Reseller reseller = resellerDAO.findById(1);
        Plant plant = plantDAO.findById(3);
        reseller = plantDAO.addPlantToReseller((int) reseller.getId(), (int) plant.getId());
        assertEquals(3, reseller.getPlant().size());
    }

    @Test
    void testGetPlantsByReseller() {
        Reseller reseller = resellerDAO.findById(1);
        assertEquals(2, plantDAO.getPlantsByReseller((int) reseller.getId()).size());
    }
}