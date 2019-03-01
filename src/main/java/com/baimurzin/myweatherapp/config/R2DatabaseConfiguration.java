package com.baimurzin.myweatherapp.config;

import com.baimurzin.myweatherapp.repository.CityRegistryRepository;
import com.baimurzin.myweatherapp.repository.CityRepository;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

/**
 * Class describes configuration for connectivity with DB
 * and beans to work with database in specific reactive way.
 *
 * @author Vladislav Baimurzin
 */
@Configuration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class R2DatabaseConfiguration {

    @Bean
    public CityRegistryRepository cityRegistryRepository() {
        return r2dbcRepositoryFactory().getRepository(CityRegistryRepository.class);
    }

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
    public DatabaseClient databaseClient() {
        return DatabaseClient.create(connectionFactory());
    }

    @Bean
    public H2ConnectionFactory connectionFactory() {
        H2ConnectionConfiguration config = H2ConnectionConfiguration.builder()
                .inMemory("testdb")
                .username("sa")
                .build();
        return new H2ConnectionFactory(config);
    }


}
