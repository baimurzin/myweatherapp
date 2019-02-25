package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.CityRegistry;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * Jpa repository not-reactive.
 * Used for manage {@link CityRegistry} domain
 *
 * @author Vladislav Baimurzin
 */
public interface CityRegistryRepository extends R2dbcRepository<CityRegistry, Long> {
}
