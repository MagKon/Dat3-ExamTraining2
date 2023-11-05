package dat3.persistence.DAO;

import dat3.config.boilerplate.DAO;
import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import dat3.persistence.Reseller_Plant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlantDAO extends DAO<Plant> implements IPlantDAO {
    public PlantDAO(Class<Plant> entityClass, EntityManagerFactory emf) {
        super(entityClass, emf);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantDAO.class);

    @Override
    public void delete(Plant plant) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                // Fetch all Reseller_Plant entries associated with the Plant
                List<Reseller_Plant> resellerPlants = entityManager.createQuery(
                                "SELECT rp FROM Reseller_Plant rp WHERE rp.plant.id = :plantId", Reseller_Plant.class)
                        .setParameter("plantId", plant.getId())
                        .getResultList();

                // Remove associations from Reseller_Plant and update the related Reseller entities
                for (Reseller_Plant resellerPlant : resellerPlants) {
                    resellerPlant.getReseller().getPlant().remove(resellerPlant);
                    entityManager.merge(resellerPlant.getReseller());
                    entityManager.remove(resellerPlant);
                }

                // Fetch the Plant entity to ensure it's in the managed state
                Plant managedPlant = entityManager.find(Plant.class, plant.getId());

                if (managedPlant != null) {
                    entityManager.remove(managedPlant);
                } else {
                    throw new EntityNotFoundException("Plant not found in the database.");
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                LOGGER.error("Error occurred during deletion: {}", e.getMessage(), e);
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
            // Rethrow the exception or handle it as needed
        }
    }

    public List<Plant> getByType(String type) {
        List<Plant> plants = null;

        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            plants = entityManager.createQuery("SELECT p FROM Plant p WHERE p.plantType = :type", Plant.class)
                    .setParameter("type", type)
                    .getResultList();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return plants;
    }

    // Returns null if the plant was not found, otherwise returns the plant
    public Plant add(PlantDto plantDto) {
        if (validateDto(plantDto)) {
            Plant plant = convertToEntity(plantDto);
            if(super.save(plant)) {
                return plant;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public Reseller addPlantToReseller(int resellerId, int plantId) {
        Reseller reseller;

        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {

            reseller = entityManager.find(Reseller.class, resellerId);
            Plant plant = entityManager.find(Plant.class, plantId);

            if (reseller == null || plant == null) {
                return null;
            }

            reseller.addPlant(plant);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return reseller;
    }

    public List<Plant> getPlantsByReseller(int resellerId) {
        List<Plant> plants;

        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            plants = entityManager.createQuery("SELECT p FROM Plant p INNER JOIN Reseller_Plant rp ON rp.reseller.id = :resellerId WHERE p = rp.plant", Plant.class)
                    .setParameter("resellerId", resellerId)
                    .getResultList();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return plants;
    }

    // Converters
    public static PlantDto convertToDto(Plant plant) {
        return new PlantDto((Integer) plant.getId(), plant.getPlantType(), plant.getPlantName(), plant.getPrice(), plant.getHeight());
    }

    public static Plant convertToEntity(PlantDto plantDto) {
        return new Plant(plantDto.id(), plantDto.plantType(), plantDto.plantName(), plantDto.price(), plantDto.height());
    }

    // Validators
    public static boolean validateDto(PlantDto plantDto) {
        return validatePlantType(plantDto.plantType()) && validatePlantName(plantDto.plantName()) && validatePrice(plantDto.price()) && validateHeight(plantDto.height());
    }
    public static boolean validatePlantType(String plantType) {
        return plantType != null && !plantType.isEmpty();
    }

    public static boolean validatePlantName(String plantName) {
        return plantName != null && !plantName.isEmpty();
    }

    public static boolean validatePrice(double price) {
        return price > 0;
    }

    public static boolean validateHeight(float height) {
        return height > 0;
    }

    public static boolean validatePrimaryKey(Object id) {
        return id instanceof Integer && (Integer) id > 0;
    }
}
