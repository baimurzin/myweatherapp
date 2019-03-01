package com.baimurzin.myweatherapp.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Part of {@link WeatherResponse}.
 *
 * @author Vladislav Baimurzin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind {

    private Double speed;

}
