package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.CityRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository not-reactive.
 * Used for manage {@link CityRegistry} domain
 *
 * @author Vladislav Baimurzin
 */
public interface CityRegistryRepository extends JpaRepository<CityRegistry, Long> {
}
