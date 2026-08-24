package com.shoply.infrastructure.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import java.net.URI;

/**
 * Resolves database connections automatically on cloud platforms like Render.
 * If SPRING_DATASOURCE_URL is not set, but Render's standard DATABASE_URL is present,
 * it parses DATABASE_URL (postgres://...) and configures the datasource JDBC URL, username, and password.
 */
@Configuration
public class DatabaseUrlResolver implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSourceProperties) {
            DataSourceProperties properties = (DataSourceProperties) bean;

            // If SPRING_DATASOURCE_URL is explicitly set, respect it and let Spring Boot configure standard properties.
            String springDatasourceUrl = System.getenv("SPRING_DATASOURCE_URL");
            if (springDatasourceUrl != null && !springDatasourceUrl.isEmpty()) {
                return bean;
            }

            // Fallback: auto-parse DATABASE_URL (automatically provided by Render when linking a Postgres DB)
            String databaseUrl = System.getenv("DATABASE_URL");
            if (databaseUrl != null && !databaseUrl.isEmpty()) {
                try {
                    if (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://")) {
                        URI uri = new URI(databaseUrl);
                        String userInfo = uri.getUserInfo();
                        if (userInfo != null && userInfo.contains(":")) {
                            String[] parts = userInfo.split(":", 2);
                            properties.setUsername(parts[0]);
                            properties.setPassword(parts[1]);
                        }

                        String host = uri.getHost();
                        int port = uri.getPort();
                        if (port == -1) {
                            port = 5432;
                        }
                        String path = uri.getPath();

                        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;
                        String query = uri.getQuery();
                        if (query != null) {
                            jdbcUrl += "?" + query;
                        } else {
                            jdbcUrl += "?sslmode=require";
                        }

                        properties.setUrl(jdbcUrl);
                    } else {
                        properties.setUrl(databaseUrl);
                    }
                } catch (Exception e) {
                    // Fail-safe simple replacement if full URI parsing fails
                    if (databaseUrl.startsWith("postgres://")) {
                        properties.setUrl(databaseUrl.replace("postgres://", "jdbc:postgresql://"));
                    } else if (databaseUrl.startsWith("postgresql://")) {
                        properties.setUrl(databaseUrl.replace("postgresql://", "jdbc:postgresql://"));
                    } else {
                        properties.setUrl(databaseUrl);
                    }
                }
            }
        }
        return bean;
    }
}
