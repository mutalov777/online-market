package uz.mutalov.onlinemarket;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import uz.mutalov.onlinemarket.property.OpenApiProperties;
import uz.mutalov.onlinemarket.property.ServerProperties;
import uz.mutalov.onlinemarket.repository.AuthUserRepository;

@SpringBootApplication
@OpenAPIDefinition
@EnableConfigurationProperties(
        {ServerProperties.class,
                OpenApiProperties.class}
)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class OnlineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }

    @Autowired
    private AuthUserRepository repository;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> repository.getUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
