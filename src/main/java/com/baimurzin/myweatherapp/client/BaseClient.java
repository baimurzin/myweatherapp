package com.baimurzin.myweatherapp.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

/**
 * Non-blocking reactive client for todo
 *
 * @author Vladislav Baimurzin
 * @version 0.1
 */
@Slf4j
public class BaseClient {

    /**
     *
     */
    private final WebClient webClient;

    /**
     * Class constructor to build WebClient from  passed base URL
     *
     * @param baseUrl
     * @see WebClient
     */
    public BaseClient(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


    /**
     * Returns an ClientResponse todo
     *
     * @param uri
     * @param variables
     * @param headers   the headers of http request
     * @param mediaType
     * @return the ClientResponse wrapped to Mono
     */
    protected Mono<ClientResponse> getClient(String uri, Map<String, String> variables,
                                             Map<String, String> headers, MediaType mediaType) {
        log.debug("Call started");
        return webClient.get()
                .uri(uriBuilder -> buildQuery(uriBuilder, uri, variables))
//                .headers(cons -> headers != null ? cons.setAll(headers) : log.info("");)
               .accept(mediaType)
                .exchange();
    }

    /**
     * Returns URI built from passed parameters
     *
     * @param uriBuilder
     * @param uri
     * @param variables
     * @return the URI for further request
     */
    private URI buildQuery(UriBuilder uriBuilder, String uri, Map<String, String> variables) {
        uriBuilder.replacePath(uri);
        variables.forEach(uriBuilder::queryParam);
        return uriBuilder.build();
    }
}
