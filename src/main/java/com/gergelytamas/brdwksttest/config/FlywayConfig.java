package com.gergelytamas.brdwksttest.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.flyway.enabled")
public class FlywayConfig {

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer(final Flyway flyway) {
        return new FlywayMigrationInitializer(flyway);
    }
}
