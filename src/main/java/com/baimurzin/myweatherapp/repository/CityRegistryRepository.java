package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.CityRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRegistryRepository extends JpaRepository<CityRegistry, Long> {
}
