package com.baimurzin.myweatherapp.job;

import com.baimurzin.myweatherapp.service.CityService;
import com.baimurzin.myweatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherCacheWarmupServiceTask {

    private final WeatherService weatherService;
    private final CityService cityService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateWeatherForRegisteredCities() {
        log.debug("Task started...");
        cityService.findAll()
                .flatMap(weatherService::getWeather)
                .subscribeOn(Schedulers.elastic())
                .subscribe();
        log.debug("Task finished.");
    }
}
