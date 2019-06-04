package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

  private static void createSchema(DataSource ds, String ddlScript) {
    try {
      DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
      InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
      if (is == null) throw new IllegalArgumentException(
        String.format("File %s not found in classpath.", ddlScript));
      scriptRunner.runScript(new InputStreamReader(is));
    }
    catch (SQLException | IOException e) {
      e.printStackTrace();
      return;
    }
  }

  private static void testJdbc() {

    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

      printTitle("create schema", 60, '-');
      createSchema(factory.getBean("dataSource", DataSource.class),
        "swt6/spring/worklog/test/CreateWorklogDbSchema.sql"); //wird bei jedem starten hochgefahren

      EmployeeDao emplDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

      printTitle("insert employee", 60, '-');
      Employee employee1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
      emplDao.insert(employee1);
      System.out.println("empl1 = " + employee1);


      printTitle("update employee", 60, '-');
      employee1.setFirstName("Jaquira");
      employee1 = emplDao.merge(employee1);
      System.out.println("empl1 = " + employee1);


      printTitle("find employee", 60, '-');
      Employee employee = emplDao.findById(1L);
      System.out.println("empl1 = " + employee);

      printTitle("find all employees", 60, '-');
      emplDao.findAll();

      // TODO: ...

    }
  }

  @SuppressWarnings("Duplicates")
  private static void testJpa() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
      EmployeeDao emplDao = factory.getBean("emplDaoJpa", EmployeeDao.class);

      printTitle("insert employee", 60, '-');
      Employee employee1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));

      JpaUtil.beginTransaction(emFactory);

      emplDao.insert(employee1);
      System.out.println("empl1 = " + employee1);


      printTitle("update employee", 60, '-');
      employee1.setFirstName("Jaquira");
      employee1 = emplDao.merge(employee1);
      System.out.println("empl1 = " + employee1);


      Long id = employee1.getId();
      JpaUtil.commitTransaction(emFactory);


      printTitle("find employee", 60, '-');

      JpaUtil.beginTransaction(emFactory);
      Employee employee = emplDao.findById(id);
      System.out.println("empl1 = " + employee);

      printTitle("find all employees", 60, '-');
      emplDao.findAll().forEach(System.out::println);

      JpaUtil.commitTransaction(emFactory);

    }
  }

  private static void testSpringData() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);

      JpaUtil.executeInTransaction(emFactory, () -> {
        EmployeeRepository emploRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

        Employee empl1 = new Employee("Josef", "Himmelbauer", LocalDate.of(1950, 1, 1));
        Employee empl2 = new Employee("Karl", "Himmelbauer", LocalDate.of(1949, 1, 1));

        printTitle("save employee", 60,'-');
        empl1 = emploRepo.save(empl1);
        empl2 = emploRepo.save(empl2);
        emploRepo.flush();

        printTitle("update employee",60,'-');
        empl1.setLastName("Huber");
        empl1 = emploRepo.save(empl1);

        Optional<Employee> emplOpt =  emploRepo.findByLastName("Himmelbauer");
        if(emplOpt.isPresent())
          System.out.println(emplOpt.get());

        List<Employee> emplOpt1 = emploRepo.findByLastNameContaining("immel");
        if(emplOpt.isPresent()){
          for(Employee e : emplOpt1)
            System.out.println(e);
        }


      });

    }
  }

  // Java 9: run with 
  // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang
  // .invoke=ALL-UNNAMED
  public static void main(String[] args) {

    printSeparator(60);
    printTitle("testJDBC", 60);
    printSeparator(60);
    testJdbc();

    printSeparator(60);
    printTitle("testJpa", 60);
    printSeparator(60);
    testJpa();


    printSeparator(60);
    printTitle("testSpringData", 60);
    printSeparator(60);
    testSpringData();
  }
}
