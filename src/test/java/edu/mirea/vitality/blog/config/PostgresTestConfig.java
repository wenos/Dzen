package edu.mirea.vitality.blog.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Конфигурация для тестов, которая запускает контейнер с PostgreSQL
 */
public class PostgresTestConfig {
    private static volatile PostgreSQLContainer<?> postgreSQLContainer;


    /**
     * Создает контейнер с PostgreSQL, если он еще не создан, и возвращает его
     *
     * @return PostgreSQLContainer
     */
    public static PostgreSQLContainer<?> getInstance() {
        var container = postgreSQLContainer;
        if (container == null) {
            synchronized (PostgresTestConfig.class) {
                container = postgreSQLContainer;
                if (container == null) {
                    postgreSQLContainer = container = new PostgreSQLContainer<>("postgres:16.0")
                            .withDatabaseName("tinkoff_neninja")
                            .withUsername("postgres")
                            .withPassword("root")
                            .withReuse(true);
                    container.start();
                }
            }
        }
        return container;
    }

    /**
     * Инициализатор контекста, который запускает контейнер с PostgreSQL
     */
    @Component("PostgresInitializer")
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var container = getInstance();

            var jdbcUrl = container.getJdbcUrl();
            var username = container.getUsername();
            var password = container.getPassword();

            TestPropertyValues.of(
                    "spring.datasource.url=" + jdbcUrl,
                    "spring.datasource.username=" + username,
                    "spring.datasource.password=" + password
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
