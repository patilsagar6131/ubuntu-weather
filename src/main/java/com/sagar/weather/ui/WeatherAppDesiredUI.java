package com.sagar.weather.ui;

import java.io.FileInputStream;

import com.sagar.weather.service.WeatherService;
import com.sagar.weather.service.WeatherViewModel;
import com.sagar.weather.util.Constants;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.bytebuddy.matcher.IsNamedMatcher;

public class WeatherAppDesiredUI extends Application {
    private String myCity;
    private String myDescription;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WeatherService service = new WeatherService(Constants.API_KEY);
        Image image1 = new Image(new FileInputStream("src/main/java/com/sagar/weather/img/settings.png"));
        Image image2 = new Image(new FileInputStream("src/main/java/com/sagar/weather/img/refresh.png"));
        ImageView view1 = new ImageView(image1);
        ImageView view2 = new ImageView(image2);
        view1.setFitHeight(30);
        view1.setFitWidth(30);
        view2.setFitHeight(30);
        view2.setFitWidth(30);
        Button img1 = new Button();
        Button img2 = new Button();
        img1.setGraphic(view1);
        img2.setGraphic(view2);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_RIGHT);
        hbox.getChildren().addAll(img2, img1);
        hbox.setSpacing(10);

        HBox hbox2 = new HBox();
        Label label1 = new Label("Your City");
        label1.setFont(new Font("Arial", 50));
        label1.setTextFill(Color.WHITE);
        Label label2 = new Label("Your feels like. . . ");
        label2.setFont(new Font("Arial", 50));
        label2.setTextFill(Color.WHITE);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(label1, label2);
        hbox2.setSpacing(10);

        ImageView temperature = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/temp.png")));
        temperature.setFitHeight(20);
        temperature.setFitWidth(20);

        ImageView maxTemp = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/max.png")));
        maxTemp.setFitHeight(20);
        maxTemp.setFitWidth(20);

        ImageView minTemp = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/min.png")));
        minTemp.setFitHeight(20);
        minTemp.setFitWidth(20);

        ImageView speed = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/speed.png")));
        speed.setFitHeight(20);
        speed.setFitWidth(20);

        ImageView pressure = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/pressure.png")));
        pressure.setFitHeight(20);
        pressure.setFitWidth(20);

        ImageView sunrise = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/sunrise.png")));
        sunrise.setFitHeight(20);
        sunrise.setFitWidth(20);

        ImageView sunset = new ImageView(
                new Image(new FileInputStream("src/main/java/com/sagar/weather/img/sunset.png")));
        sunset.setFitHeight(20);
        sunset.setFitWidth(20);

        // labels
        Label tempLabel = new Label();
        tempLabel.setTextFill(Color.WHITE);
        Label maxTempLabel = new Label();
        maxTempLabel.setTextFill(Color.WHITE);
        Label minTempLabel = new Label();
        minTempLabel.setTextFill(Color.WHITE);
        Label speedLabel = new Label();
        speedLabel.setTextFill(Color.WHITE);
        Label pressureLabel = new Label();
        pressureLabel.setTextFill(Color.WHITE);
        Label sunriseLabel = new Label();
        sunriseLabel.setTextFill(Color.WHITE);
        Label sunsetLabel = new Label();
        sunsetLabel.setTextFill(Color.WHITE);

        GridPane conditionsLayout = new GridPane();
        conditionsLayout.setHgap(20);
        conditionsLayout.setVgap(10);
        conditionsLayout.setAlignment(Pos.CENTER);
        conditionsLayout.add(temperature, 0, 0);
        conditionsLayout.add(tempLabel, 1, 0);
        conditionsLayout.add(maxTemp, 0, 1);
        conditionsLayout.add(maxTempLabel, 1, 1);
        conditionsLayout.add(minTemp, 0, 2);
        conditionsLayout.add(minTempLabel, 1, 2);
        conditionsLayout.add(speed, 0, 3);
        conditionsLayout.add(speedLabel, 1, 3);
        conditionsLayout.add(pressure, 0, 4);
        conditionsLayout.add(pressureLabel, 1, 4);
        conditionsLayout.add(sunrise, 0, 5);
        conditionsLayout.add(sunriseLabel, 1, 5);
        conditionsLayout.add(sunset, 0, 6);
        conditionsLayout.add(sunsetLabel, 1, 6);

        img1.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("City popup");
            dialog.setHeaderText("Enter city");
            dialog.setContentText("Enter your city");

            dialog.showAndWait().ifPresent(city -> {
                myCity = city;
                service.fetchWeather(myCity).thenAccept(response -> {
                    WeatherViewModel vm = new WeatherViewModel(response);

                    Platform.runLater(() -> {
                        myDescription = vm.getDescription();
                        label1.setText(vm.getCityName() + " : ");
                        label2.setText(capitalize(vm.getDescription()));
                        tempLabel.setText(vm.getTemperature());
                        maxTempLabel.setText(vm.getTemp_max());
                        minTempLabel.setText(vm.getTemp_min());
                        speedLabel.setText(vm.getSpeed());
                        pressureLabel.setText(vm.getPressure());
                        sunriseLabel.setText(vm.getSunrise());
                        sunsetLabel.setText(vm.getSunset());
                    });
                });
            });
        });

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(20);
        vBox1.getChildren().addAll(conditionsLayout);

        VBox root = new VBox();
        root.getChildren().addAll(hbox, hbox2, vBox1);
        root.setStyle("-fx-background-color: #7c6af1ff");
        root.setSpacing(20);

        Scene scene = new Scene(root, 500, 500, Color.BLUE);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(WeatherAppDesiredUI.class, args);
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
