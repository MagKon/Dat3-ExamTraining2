package dat3.persistence.DAO;

import dat3.config.boilerplate.DAO;
import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import dat3.persistence.Reseller_Plant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlantDAO extends DAO<Plant> implements IPlantDAO {
    public PlantDAO(Class<Plant> entityClass, EntityManagerFactory emf) {
        super(entityClass, emf);
    }

    @Override
    public void delete(Plant plant) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            for (Reseller_Plant reseller_plant : plant.getReseller_plants()) {
                reseller_plant.setPlant(null);
            }

            entityManager.getTransaction().commit();

            super.delete(plant);

        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
            plants = entityManager.createQuery("SELECT p FROM Plant p JOIN Reseller_Plant rp ON rp.reseller.id = :resellerId", Plant.class)
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
