package com.baimurzin.myweatherapp.repository;

import com.baimurzin.myweatherapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
