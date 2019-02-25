package com.baimurzin.myweatherapp.service.impl;

import com.baimurzin.myweatherapp.client.dto.WeatherResponse;
import com.baimurzin.myweatherapp.client.weather.WeatherClient;
import com.baimurzin.myweatherapp.model.City;
import com.baimurzin.myweatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Basic implementation of {@link WeatherService}
 *
 * @author Vladislav Baimurzin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    /**
     * The name of cache that used for caching weather data
     */
    private static final String CACHE_NAME = "WEATHER_CACHE";

    private final WeatherClient weatherClient;

    private final CacheManager cacheManager;

    @Override
    public Mono<WeatherResponse> getWeather(City city) {
        log.debug("Retrieving the weather for city: {} with city ID: {}", city.getCityName(), city.getCityId());
        return CacheMono.lookup(getWeatherByCityReader(), city.getCityId())
                .onCacheMissResume(weatherClient.getWeatherByCity(city))
                .andWriteWith(getWeatherWriter());
    }

    @Override
    public Flux<WeatherResponse> getWeatherForAllCities(Flux<City> cities) {
        return cities.subscribeOn(Schedulers.elastic())
                .flatMap(this::getWeather);
    }

    @Override
    public Mono<Void> deleteWeatherData(Long city) {
        log.debug("Trying to evict cache: {} for this key: {}", CACHE_NAME, city);
        return Mono.fromRunnable(() -> cacheManager.getCache(CACHE_NAME).evict(city));
    }

    /**
     * The {@link Function} that looks up {@link Signal} from a cache, returning
     *      *  them as a {@link Mono Mono&lt;Signal&gt;}
     * @return this reader function
     */
    @SuppressWarnings("unchecked")
    private Function<Long, Mono<Signal<? extends WeatherResponse>>> getWeatherByCityReader() {
        log.debug("Trying to read from cache first...");
        return k -> {
            Cache.ValueWrapper wrapper = cacheManager.getCache(CACHE_NAME).get(k);
            if (wrapper == null) return Mono.justOrEmpty(Optional.empty());
            return Mono.justOrEmpty((Signal<? extends WeatherResponse>) wrapper.get());
        };
    }

    /**
     * Function that used to store the source data into the cache in case of cache miss.
     *
     * @return Cache writer {@link BiFunction}
     */
    private BiFunction<Long, Signal<? extends WeatherResponse>, Mono<Void>> getWeatherWriter() {
        log.debug("Trying to put data into the cache...");
        return (key, value) -> Mono.fromRunnable(() -> cacheManager.getCache(CACHE_NAME).put(key, value));
    }

}
