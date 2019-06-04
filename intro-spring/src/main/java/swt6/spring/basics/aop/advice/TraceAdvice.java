package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceAdvice {

    @Pointcut("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
    private void findMethods(){
        //dummy methode als ref für pointcut
    }


    @Before("findMethods()")
    public void traceBefore(JoinPoint jp){
        String methodName = jp.getTarget().getClass().getName() + "."+ jp.getSignature().getName();
        System.out.println("--> " + methodName);
    }

    @After("execution(public * swt6.spring.basics.aop.logic..*find*(..))") //obige kann wiederverwendet werden, hier muss ich immer kopieren
    public void traceAfter(JoinPoint jp){ //belibieger name
        String methodName = jp.getTarget().getClass().getName() + "."+ jp.getSignature().getName();
        System.out.println("<-- " + methodName);
    }

    @Around("findMethods()")
    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getTarget().getClass().getName() + "."+ pjp.getSignature().getName();
        System.out.println( "==> " + methodName);
        Object retVal = pjp.proceed(); // execute the advice method
        System.out.println("<== " + methodName);
        return retVal;
    }

    @AfterThrowing(value = "findMethods()", throwing = "exception")
    public void traceException(JoinPoint jp, Throwable exception){
        String methodName = jp.getTarget().getClass().getName() + "."+ jp.getSignature().getName();
        System.out.printf("##> %s%n threw Exception <%s>%n", methodName, exception);
    }
}
