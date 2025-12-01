package com.sagar.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.sagar.weather.service.WeatherService;

/**
 * Hello world!
 */
public class App {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        System.out.println("Hello World!");
        var wf = new WeatherService("PASTE_YOUR_API_KEY");
        // wf.fetchByCity("Delhi").thenAccept(response -> {
        // System.out.println(response);
        // }).join();

        wf.fetchWeather("Delhi").thenAccept(weather -> {
            try {
                String pretty = MAPPER.writeValueAsString(weather);
                System.out.println(pretty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).join();

    }
}
