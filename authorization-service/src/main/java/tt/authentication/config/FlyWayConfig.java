package tt.authentication.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlyWayConfig {
    private final Environment environment;

    @Autowired
    public FlyWayConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
                .validateMigrationNaming(true)
                .baselineOnMigrate(true)
                .dataSource(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
                )
        );
    }
}
