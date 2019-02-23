package com.baimurzin.myweatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "city_registry")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityRegistry {

    @Id
    @Column(name = "city_id")
    private Long id;

    @Column(name = "city_name")
    private String name;

    private String country;

    private Double lat;

    private Double lon;
}
