package dat3.persistence;

/**
 * This interface is used by the MemoryDAO class to ensure that the entities have an id.
 * It is not necessary to implement this interface if you are not using the MemoryDAO class.
 * @see dat3.persistence.DAO.MemoryDAO
 */
public interface IEntity {
    Object getId();
    void setId(Object id);
}
