package dat3.config.boilerplate;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * This is an interface for making a DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 * It is expected that the children of this interface will accept a Class<T> in their constructor.
 *
 * @param <T>
 */
public interface IDAO<T> {
    Class<T> getEntityClass();

    EntityManagerFactory getEntityManagerFactory();

    void setEntityManagerFactory(EntityManagerFactory emf);

    T findById(Object id);

    List<T> getAll();

    Boolean save(T t);

    T merge(T t);

    void delete(T t);

    void truncate();

    void close();
}
