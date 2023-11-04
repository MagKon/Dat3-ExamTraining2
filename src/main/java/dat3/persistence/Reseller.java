package dat3.persistence;

import dat3.config.HibernateConfig;
import dat3.config.boilerplate.DAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reseller implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String phone;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Reseller_Plant> plant = new ArrayList<>();

    public void addPlant(Plant plant) {
        DAO<Reseller_Plant> reseller_plantDAO = new DAO<>(Reseller_Plant.class, HibernateConfig.getEntityManagerFactory());

        Reseller_Plant reseller_plant = new Reseller_Plant();
        reseller_plant.setPlant(plant);
        reseller_plant.setReseller(this);

        reseller_plant = reseller_plantDAO.merge(reseller_plant);

        this.plant.add(reseller_plant);
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
