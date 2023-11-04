package dat3.persistence.DAO;

import dat3.config.boilerplate.IDAO;
import dat3.persistence.DTO.PlantDto;
import dat3.persistence.Plant;
import dat3.persistence.Reseller;

import java.util.List;

public interface IPlantDAO extends IDAO<Plant> {
    List<Plant> getByType(String type);

    Plant add(PlantDto plantDto);

    Reseller addPlantToReseller(int resellerId, int plantId);

    List<Plant> getPlantsByReseller(int resellerId);
}
