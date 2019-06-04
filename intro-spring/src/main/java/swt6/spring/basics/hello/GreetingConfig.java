package swt6.spring.basics.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfig {

    @Bean(name="greetingService")
    public GreetingService getGreetingService(){
        GreetingService greetingService = new GreetingServiceImpl();
        greetingService.setMessage("Hello Java based config");
        return  greetingService;
    }
}
