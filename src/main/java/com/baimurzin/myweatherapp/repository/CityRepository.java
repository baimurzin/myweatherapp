package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository not-reactive.
 * Used for manage {@link City} domain
 *
 * @author Vladislav Baimurzin
 */
public interface CityRepository extends JpaRepository<City, Long> {
}
