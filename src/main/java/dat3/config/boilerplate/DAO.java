package dat3.config.boilerplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.List;

/**
 * This class is a generic DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 * @param <T> The entity class that the DAO should be used for.
 */
@Getter
public class DAO<T> extends ADAO<T> {
    // Constructors

    /**
     * This constructor creates a DAO with the entity class specified. It is expected that the EntityManagerFactory is set later.
     * @param entityClass The entity class that the DAO should be used for. This cannot be changed later.
     */
    public DAO(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * This constructor creates a DAO with the entity class and EntityManagerFactory specified.
     * @param entityClass The entity class that the DAO should be used for. This cannot be changed later.
     * @param emf The EntityManagerFactory that the DAO should use to create EntityManagers.
     */
    public DAO(Class<T> entityClass, EntityManagerFactory emf) {
        super(entityClass, emf);
    }
}

