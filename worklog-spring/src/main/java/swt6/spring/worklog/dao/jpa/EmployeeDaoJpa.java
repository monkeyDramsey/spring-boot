package swt6.spring.worklog.dao.jpa;

import org.springframework.dao.DataAccessException;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeDaoJpa implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;
    //wird automatisch von spring injected

    @Override
    public Employee findById(Long id) throws DataAccessException {
        return em.find(Employee.class, id);
    }

    @Override
    public List<Employee> findAll() throws DataAccessException {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Override
    public void insert(Employee entity) throws DataAccessException {
        Employee persistentEmpl = em.merge(entity);
        entity.setId( persistentEmpl.getId());
    }

    @Override
    public Employee merge(Employee entity) throws DataAccessException {
        return em.merge(entity);
    }
}
