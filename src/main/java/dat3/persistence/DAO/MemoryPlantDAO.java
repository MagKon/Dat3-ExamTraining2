package dat3.persistence.DAO;

import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import dat3.persistence.Reseller_Plant;

import java.util.Comparator;
import java.util.List;

public class MemoryPlantDAO extends MemoryDAO<Plant>  implements IPlantDAO {
    public MemoryPlantDAO(Class<Plant> entityClass) {
        super(entityClass);
    }

    public List<Plant> getMaxHeightOf100() {
        return super.getAll().stream().filter(plant -> plant.getHeight() == 100).toList();
    }

    public List<String> getPlantNames() {
        return super.getAll().stream().map(Plant::getPlantName).toList();
    }

    public List<Plant> sortByName(List<Plant> plants) {
        return plants.stream().sorted(Comparator.comparing(Plant::getPlantName)).toList();
    }

    @Override
    public List<Plant> getByType(String type) {
        return super.getAll().stream().filter(plant -> plant.getPlantType().equals(type)).toList();
    }

    @Override
    public Plant add(PlantDto plantDto) {
        if (PlantDAO.validateDto(plantDto)) throw new IllegalArgumentException("Invalid plant");
        Plant plant = PlantDAO.convertToEntity(plantDto);
        super.save(plant);
        return plant;
    }

    @Override
    public Reseller addPlantToReseller(int resellerId, int plantId) {
        Reseller reseller = new MemoryResellerDAO(Reseller.class).findById(resellerId);
        Plant plant = super.findById(plantId);
        if (reseller == null || plant == null) throw new IllegalArgumentException("Invalid reseller or plant");
        reseller.addPlant(plant);
        return reseller;
    }

    @Override
    public List<Plant> getPlantsByReseller(int resellerId) {
        Reseller reseller = new MemoryResellerDAO(Reseller.class).findById(resellerId);
        if (reseller == null) throw new IllegalArgumentException("Invalid reseller");

        return reseller.getPlant().stream().map(Reseller_Plant::getPlant).toList();
    }
}
