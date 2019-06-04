package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> { //Long typ von Id

    Optional<Employee> findByLastName(@Param("name") String lastName); //findet lastname wegen LastName in findByLastName

    @Query("select e from Employee e where e.lastName like %:substr%")
    List<Employee> findByLastNameContaining(@Param("substr") String substr);
}
