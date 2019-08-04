package pl.edu.kopalniakodu.pickide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableJpaAuditing
public class PickIdeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickIdeApplication.class, args);

    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PickIdeApplication.class);
    }

}
