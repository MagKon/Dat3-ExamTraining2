package dat3.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Plant implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String plantType;

    private String plantName;

    private double price;

    private float height;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reseller_Plant> reseller_plants = new ArrayList<>();

    public Plant(int id, String plantType, String plantName, double price, float height) {
        this.id = id;
        this.plantType = plantType;
        this.plantName = plantName;
        this.price = price;
        this.height = height;
    }

    @Override
    public void setId(Object id) {
        this.id = (int) id;
    }

    @Override
    public Object getId() {
        return id;
    }
}
