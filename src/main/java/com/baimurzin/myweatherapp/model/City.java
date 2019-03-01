package com.baimurzin.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * An Entity representation. Describe city object
 *
 * @author Vladislav Baimurzin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("cities")
public class City implements Persistable<Long> {

    @Transient
    @JsonIgnore
    private boolean isNew;

    @Transient
    @JsonIgnore
    private Long id;

    @Id
    @Column("city_id")
    private Long cityId;

    @Column("city_name")
    private String cityName;

    public City(Long id, String name) {
        this.cityId = id;
        this.cityName = name;
    }

    @Override
    @Transient
    public Long getId() {
        return cityId;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isNew() {
        return true;
    }
}
