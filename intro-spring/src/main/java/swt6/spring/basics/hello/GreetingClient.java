package swt6.spring.basics.hello;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {

    public static void main(String[] args) {

        try(AbstractXmlApplicationContext factory =
                    new ClassPathXmlApplicationContext("swt6/spring/basics/hello/GreetingService.xml")) {
            GreetingService bean = factory.getBean("const-greetingService", GreetingService.class);
            bean.sayHello();
        }

        BeanFactory beanFactory = new AnnotationConfigApplicationContext(GreetingConfig.class);
        GreetingService bean = beanFactory.getBean("greetingService", GreetingService.class);
        bean.sayHello();



    }
}
