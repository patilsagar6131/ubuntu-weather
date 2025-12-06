package com.sagar.weather.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WeatherViewModel {
    private final WeatherResponse data;

    public WeatherViewModel(WeatherResponse data) {
        this.data = data;
    }

    public String getTemperature() {
        return String.format("%.1f°C", data.getMain().getTemp());
    }

    public String getFeels_like() {
        return String.format("%.1f°C", data.getMain().getFeels_like());
    }

    public String getTemp_min() {
        return String.format("%.1f°C", data.getMain().getTemp_min());
    }

    public String getTemp_max() {
        return String.format("%.1f°C", data.getMain().getTemp_max());
    }

    public String getSpeed() {
        double speedkmph = data.getWind().getSpeed() * 3.6;
        return String.format("%.1f km/h   %s", speedkmph, windDirection());
    }

    public String getSunrise() {
        return formatTime(data.getSys().getSunrise(), data.getTimezone());
    }

    public String getSunset() {
        return formatTime(data.getSys().getSunset(), data.getTimezone());
    }

    public String formatTime(long unix, long timeZoneOffset) {
        long adjusted = unix + timeZoneOffset;
        Instant instant = Instant.ofEpochSecond(adjusted);
        return DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.of("UTC")).format(instant);
    }

    public String getIconPath() {
        String iconCode = data.getWeather().get(0).getIcon();
        return "/icons/" + iconCode + ".png";
    }

    public String getCityName() {
        return data.getName();
    }

    public String getDescription() {
        return data.getWeather().get(0).getDescription();
    }

    public String windDirection() {
        if (data.getWind().getDeg() == 0) {
            return "North";
        }
        if (data.getWind().getDeg() > 0 && data.getWind().getDeg() < 90) {
            return "North-East";
        } else if (data.getWind().getDeg() == 90) {
            return "East";
        } else if (data.getWind().getDeg() > 90 && data.getWind().getDeg() < 180) {
            return "South-East";
        } else if (data.getWind().getDeg() == 180) {
            return "South";
        } else if (data.getWind().getDeg() >= 180 && data.getWind().getDeg() < 270) {
            return "South-West";
        } else if (data.getWind().getDeg() == 270) {
            return "West";
        } else {
            return "North-West";
        }
    }

    public String getVisibility() {
        return data.getVisibility() / 1000 + " km";
    }

    public String getPressure() {
        return data.getMain().getPressure() + " hPa";
    }

}
