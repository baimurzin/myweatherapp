package com.baimurzin.myweatherapp.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    public WeatherResponse() {
    }

    @JsonCreator
    public WeatherResponse(@JsonProperty String base,
                           @JsonProperty Long visibility,
                           @JsonProperty("name") String cityName,
                           @JsonProperty("id") Long cityId) {
        this.base = base;
        this.visibility = visibility;
        this.cityName = cityName;
        this.cityId = cityId;
    }

//    private Wind wind;

//    @JsonProperty("weather")
//    private List<Weather> weather;

    private String base;

    private Long visibility;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("id")
    private Long cityId;
}
