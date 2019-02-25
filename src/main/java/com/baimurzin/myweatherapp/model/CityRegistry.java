package com.baimurzin.myweatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Table("city_registry")
//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityRegistry {

    @Id
    @Column("city_id")
    private Long id;

    @Column("city_name")
    private String name;

    private String country;

    private Double lat;

    private Double lon;
}
