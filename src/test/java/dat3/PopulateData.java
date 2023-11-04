package dat3;

import dat3.persistence.Plant;
import dat3.persistence.Reseller;
import jakarta.persistence.EntityManager;

public class PopulateData {

    public static void populate(EntityManager em) {

        em.getTransaction().begin();

        Plant plant1 = new Plant();
        plant1.setPlantName("Bonsai");
        plant1.setPlantType("Tree");
        plant1.setPrice(100);
        plant1.setHeight(0.5f);

        Plant plant2 = new Plant();
        plant2.setPlantName("Rose");
        plant2.setPlantType("Flower");
        plant2.setPrice(50);
        plant2.setHeight(0.5f);

        Plant plant3 = new Plant();
        plant3.setPlantName("Cactus");
        plant3.setPlantType("Cactus");
        plant3.setPrice(25);
        plant3.setHeight(0.5f);

        em.persist(plant1);
        em.persist(plant2);
        em.persist(plant3);

        em.getTransaction().commit();

        em.getTransaction().begin();

        Reseller reseller1 = new Reseller();
        reseller1.setName("Plantorama");
        reseller1.setAddress("Plantorama Street 1");
        reseller1.setPhone("12345678");

        em.persist(reseller1);

        em.getTransaction().commit();

        em.getTransaction().begin();

        reseller1.addPlant(plant1);
        reseller1.addPlant(plant2);

        em.getTransaction().commit();
    }
}
