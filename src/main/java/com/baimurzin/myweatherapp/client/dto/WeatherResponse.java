package com.baimurzin.myweatherapp.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Wind wind;

    @JsonProperty("weather")
    private List<Weather> weather;

    private String base;

    private Long visibility;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("id")
    private Long cityId;
}
