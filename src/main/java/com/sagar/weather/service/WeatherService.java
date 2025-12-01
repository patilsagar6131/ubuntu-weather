package com.sagar.weather.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sagar.weather.util.Constants;

public class WeatherService {
    private final String apiKey;
    private final HttpClient client;
    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public CompletableFuture<String> fetchByCity(String city) {
        Objects.requireNonNull(city, "city can not be blank");
        if (city.isBlank())
            throw new IllegalArgumentException("City cannot be blank");
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format(Constants.CITY_URL, encodedCity, apiKey);
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApplyAsync(
                response -> {
                    if (response.statusCode() >= 200 && response.statusCode() < 300) {
                        return response.body();
                    }
                    throw new RuntimeException("API ERROR " + response.statusCode());
                });
    }

    public WeatherResponse parseWeather(String json) {
        try {
            return MAPPER.readValue(json, WeatherResponse.class);
        } catch (Exception e) {
            throw new Error("Unable to convert to weather response  ", e);
        }
    }

    public CompletableFuture<WeatherResponse> fetchWeather(String city) {
        return fetchByCity(city).thenApply(this::parseWeather);
    }

}
