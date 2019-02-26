package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.City;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * Jpa repository not-reactive.
 * Used for manage {@link City} domain
 *
 * @author Vladislav Baimurzin
 */

public interface CityRepository extends R2dbcRepository<City, Long> {


}
