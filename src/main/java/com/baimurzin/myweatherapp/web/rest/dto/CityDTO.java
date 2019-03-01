package com.baimurzin.myweatherapp.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CityDTO class used for exchange data between client and server
 *
 * @author Vladislav Baimurzin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    private Long cityId;
}
