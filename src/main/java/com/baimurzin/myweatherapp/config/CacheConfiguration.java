package com.baimurzin.myweatherapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The configuration class with cache configs.
 * Will be registered in Spring context.
 *
 * @author Vladislav Baimurzin
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration implements CachingConfigurer {

    public final static String WEATHER_CACHE = "WEATHER_CACHE";

    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        log.debug("Creating cache...");
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        ArrayList<CaffeineCache> caches = new ArrayList<>(1);
        caches.add(new CaffeineCache(WEATHER_CACHE,
                Caffeine.newBuilder().recordStats()
                        //todo move configuration to property file
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .build())
        );

        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
