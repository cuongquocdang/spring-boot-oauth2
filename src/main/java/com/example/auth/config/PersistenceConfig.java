package com.example.auth.config;

import com.example.auth.dto.LocalUser;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.example.auth.util.AppConstant.NOT_AUTHENTICATED_USER_ID;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    public FlywayMigrationStrategy cleanMigrationStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            LocalUser localUser = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (null != auth && auth.getPrincipal() instanceof LocalUser) {
                localUser = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            Long id = null != localUser ? localUser.getUserFromDatabase().getId() : NOT_AUTHENTICATED_USER_ID;

            return Optional.of(id);
        };
    }

}
