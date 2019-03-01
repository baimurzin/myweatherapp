package com.baimurzin.myweatherapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * Swagger configuration class.
 * Registering swagger component for generating API docs.
 *
 * @author Vladislav Baimurzin
 */
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

}
