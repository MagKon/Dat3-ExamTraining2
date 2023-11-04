package dat3.persistence.DAO;

import dat3.persistence.Reseller;

public class MemoryResellerDAO extends MemoryDAO<Reseller> {
    public MemoryResellerDAO(Class<Reseller> entityClass) {
        super(entityClass);
    }
}
