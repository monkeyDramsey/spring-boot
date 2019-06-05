package swt6.spring.worklog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevelopementConfig {

    @Bean
    DatabaseInitializer initialize(){
        return new DatabaseInitializer();
    }

}
