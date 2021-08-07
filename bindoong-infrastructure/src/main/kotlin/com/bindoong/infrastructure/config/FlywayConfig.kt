package com.bindoong.infrastructure.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!prod")
@Configuration
class FlywayConfig(
    @Value("\${spring.flyway.url}")
    private val url: String,
    @Value("\${spring.flyway.user}")
    private val user: String,
    @Value("\${spring.flyway.password}")
    private val password: String,
) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(url, user, password)
                .locations("classpath:/db/migration")
                .validateOnMigrate(true)
                .cleanDisabled(true)
                .ignoreIgnoredMigrations(true)
                .placeholderReplacement(false)
        )
    }
}
