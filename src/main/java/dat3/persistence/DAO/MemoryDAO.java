package dat3.persistence.DAO;

import dat3.config.boilerplate.ADAO;
import dat3.persistence.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the ADAO interface. It is used as an in-memory database for assignments.
 * It is not meant to be used in production or for anything other than assignments.
 * The ADAO interface is an abstract class that implements the IDAO interface. Usually the ADAO class would access a database.
 * In this case it is used to access an in-memory list by overriding the methods.
 * @param <T>
 */
public class MemoryDAO<T extends IEntity> extends ADAO<T> {

    // A counter to keep track of the id's of the entities
    private static int idCounter = 0;

    // A list to store the entities
    private final List<T> entities = new ArrayList<>();

    // The constructor takes a class as a parameter. This is used to get the class of the entity.
    public MemoryDAO(Class<T> entityClass) {
        super(entityClass);
    }

    // The methods below are overridden from the ADAO class. They are used to access the in-memory list.

    // Find an entity by id in the list by using streams and the filter method.
    @Override
    public T findById(Object id) {
        // The filter method takes a predicate as a parameter. The predicate is a lambda expression that takes an entity as a parameter and returns a boolean.
        return entities.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    // Return the list of entities
    @Override
    public List<T> getAll() {
        return entities;
    }

    // Add an entity to the list. If the entity does not have an id, set the id to the idCounter and increment the idCounter.
    @Override
    public Boolean save(T t) {
        if (t.getId() == null) {
            t.setId(idCounter++);
        }
        entities.add(t);
        return true;
    }

    // Remove an entity from the list by using the removeIf method and a predicate.
    @Override
    public T merge(T t) {
        // RemoveIf takes a predicate as a parameter. The predicate is a lambda expression that takes an entity as a parameter and returns a boolean.
        entities.removeIf(e -> e.getId().equals(t.getId()));
        entities.add(t);
        return t;
    }

    // Remove an entity from the list by using the removeIf method and a predicate.
    @Override
    public void delete(T t) {
        // See the merge method for an explanation of the removeIf method.
        entities.removeIf(e -> e.getId().equals(t.getId()));
    }

    // Clear the list
    @Override
    public void truncate() {
        entities.clear();
    }

    // Nothing to close as this is an in-memory database. This method is just here to override the method from the ADAO class.
    @Override
    public void close() {
        // Nothing to close
    }
}
