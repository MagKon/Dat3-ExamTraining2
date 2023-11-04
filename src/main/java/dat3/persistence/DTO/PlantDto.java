package dat3.persistence.DTO;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link dat3.persistence.Plant}
 */


public record PlantDto(int id, String plantType, String plantName, double price, float height) implements Serializable {

}