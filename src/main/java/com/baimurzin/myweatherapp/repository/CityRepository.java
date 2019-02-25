package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * Jpa repository not-reactive.
 * Used for manage {@link City} domain
 *
 * @author Vladislav Baimurzin
 */
@Repository
public interface CityRepository extends R2dbcRepository<City, Long> {


}
