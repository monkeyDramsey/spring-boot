package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.sql.*;
import java.util.List;

public class EmployeeDaoJdbc extends JdbcDaoSupport implements EmployeeDao {


    //Helper class that maps resultsets to collections
    protected static class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
            return employee;
        }
    }


    public Employee findById1(Long aLong) {
        return null;
    }

    //Version 1
    @Override
    public Employee findById(Long id) {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee where id = ?";
        final Object[] params = {id};
        try {
            return getJdbcTemplate().queryForObject(sql, params, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //Version 2
    public Employee findById2(Long id) {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee where id = ?";
        final Object[] params = {id};
        List<Employee> employeeList = getJdbcTemplate().query(sql, params , new EmployeeRowMapper());
        return employeeList.size() == 0 ? null : employeeList.get(0);
    }

    //Version 3
    public Employee findById3(Long id) {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee where id = ?";
        final Employee employee = new Employee();
        getJdbcTemplate().query(sql, new Object[]{id}, (ResultSet resultSet ) -> {
            employee.setId(resultSet.getLong(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
        });
        return employee.getId() == null ? null : employee;
    }

    //Version 4:
    public Employee findById4(Long id) {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee where id = ?";
        final Employee employee = new Employee();
        final Object[] params = {id};
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setDateOfBirth(resultSet.getDate(4).toLocalDate());
            }
        };
        getJdbcTemplate().query(sql, params, rowCallbackHandler);
        return employee.getId() == null ? null : employee;
    }

    @Override
    public List<Employee> findAll() {
        String sql = "select id, firstName, lastName, dateOfBirth from Employee";
        return getJdbcTemplate().query(sql, new EmployeeRowMapper());
    }

    @Override // Version 4: if autogenerated key is needed
    public void insert(Employee entity) throws DataAccessException {
        final String sql = "insert into Employee (firstName, lastName, dateOfBirth) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update( conn -> {
            PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"id"});
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
            return stmt;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
    }

    private void update(final Employee employee) throws DataAccessException {
        final String sql = "update Employee set firstName=?, lastName=?, dateOfBirth=? where id = ?";
        getJdbcTemplate().update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                Date.valueOf(employee.getDateOfBirth()),
                employee.getId());
    }

    @Override
    public Employee merge(Employee entity) {
        if(entity.getId() == null)
            insert(entity);
        else
            update(entity);
        return entity;
    }
}