package com.baimurzin.myweatherapp.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Just a Data transfer object representation of WeatherResponse
 *
 * @author Vladislav Baimurzin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private Long id;

    private String main;

    private String description;

    private String icon;
}
