package swt6.spring.basics.aop.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogFacade;

public class AOPTest {

  // testAOP
  private static void testAOP(String configFileName) {
    try (AbstractApplicationContext factory = new
            ClassPathXmlApplicationContext(configFileName)) {
      System.out.println("----------- TraceAdvice, ProfilingAdvice -----------");
      WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
      try {
        workLog.findAllEmployees();
        workLog.findEmployeeById(3L);
        workLog.findEmployeeById(5L); //test exception
      } catch (EmployeeIdNotFoundException e) {

      }
    }
  }
// ---------------------------------------

  //TraceAdvice#traceAround
  public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
    String methodName = pjp.getTarget().getClass().getName() + "." +
            pjp.getSignature();
    System.out.println("==> " + methodName);
    Object retVal = pjp.proceed(); // delegates to method of target class.
    System.out.println("<== " + methodName);
    return retVal;
  }
// ---------------------------------------

  //TraceAdvice#traceException
  public void traceException(JoinPoint jp, Throwable exception) {
    String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
    System.out.printf("##> %s%n threw exception <%s>%n", methodName, exception);
  }
// ---------------------------------------




  public static void main(String[] args) {
    System.out.println("=============== testAOP (config based) ===============");
    testAOP("swt6/spring/basics/aop/test/applicationContext-xml-config.xml");

    // System.out.println("============= testAOP (annotation based) =============");
    // testAOP("<< insert spring config file here >>");
  }

}
