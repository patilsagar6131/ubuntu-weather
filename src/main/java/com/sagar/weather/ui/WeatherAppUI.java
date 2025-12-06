package com.sagar.weather.ui;

import com.sagar.weather.service.WeatherService;
import com.sagar.weather.service.WeatherViewModel;
import com.sagar.weather.util.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WeatherAppUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Top search bar
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter your city ");
        Button searchBtn = new Button("Search");

        HBox topBar = new HBox(10, cityInput, searchBtn);
        root.setTop(topBar);

        // Center weather info
        Label tempLabel = new Label("Temperature -- ℃");
        Label feelsLikeLabel = new Label("Feels Like -- ℃");
        Label conditionLabel = new Label("Condition -- ");
        Label minTempLabel = new Label("Minimum Temperature -- ℃");
        Label maxTempLabel = new Label("Maximum Temperature -- ℃");
        Label visibilityLabel = new Label("Visisbility -- km ");
        Label airPressureLabel = new Label("Air Pressure -- hPa");
        Label windSpeedLabel = new Label("Wind Speed -- km/h ");
        Label sunRiseLabel = new Label("Sunrise ");
        Label sunSetLabel = new Label("Sunset ");

        ImageView weatherIcon = new ImageView();
        weatherIcon.setFitHeight(80);
        weatherIcon.setFitWidth(80);

        VBox centerBox = new VBox(10, tempLabel, feelsLikeLabel, conditionLabel, minTempLabel, maxTempLabel,
                visibilityLabel, airPressureLabel, windSpeedLabel, sunRiseLabel, sunSetLabel);
        root.setCenter(centerBox);

        // Scene setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Ubuntu weather app ");
        primaryStage.setScene(scene);
        primaryStage.show();

        WeatherService service = new WeatherService(Constants.API_KEY);
        // event handling
        searchBtn.setOnAction(e -> {
            String city = cityInput.getText();
            service.fetchWeather(city).thenAccept(weather -> {
                WeatherViewModel vm = new WeatherViewModel(weather);
                Platform.runLater(() -> {
                    tempLabel.setText("Temeprature " + vm.getTemperature());
                    feelsLikeLabel.setText("Feels Like " + vm.getFeels_like());
                    conditionLabel.setText("Condition " + vm.getDescription());
                    minTempLabel.setText("Minimum Temp " + vm.getTemp_min());
                    maxTempLabel.setText("Maximum Temp " + vm.getTemp_max());
                    visibilityLabel.setText("Visibility " + vm.getVisibility());
                    airPressureLabel.setText("Air Pressure " + vm.getPressure());
                    windSpeedLabel.setText("Wind Speed " + vm.getSpeed());
                    sunRiseLabel.setText("Sunrise " + vm.getSunrise());
                    sunSetLabel.setText("Sunset " + vm.getSunset());

                });
            });
        });

    }

}
