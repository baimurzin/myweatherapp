package com.baimurzin.myweatherapp.config;

import com.baimurzin.myweatherapp.repository.CityRepository;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

@Configuration
@EnableJdbcRepositories
public class DBConfiguration {

    @Bean
    public CityRepository cityRepository() {
        return r2dbcRepositoryFactory().getRepository(CityRepository.class);
    }

    @Bean
    public R2dbcRepositoryFactory r2dbcRepositoryFactory() {
        RelationalMappingContext context = new RelationalMappingContext();
        context.afterPropertiesSet();
        return new R2dbcRepositoryFactory(databaseClient(), context,
                new DefaultReactiveDataAccessStrategy(new H2Dialect()));
    }

    @Bean
    public H2ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("test").username("sa").build());
    }

    @Bean
    public DatabaseClient databaseClient() {
        return DatabaseClient.create(connectionFactory());
    }
}
