package com.example.weatherapp;

import java.util.Calendar;

public class WeatherInformation {
    private double windSpeed;
    private double cloudiness;
    private double humidity;
    private double maxTemp;
    private double minTemp;
    private String iconName;
    private Calendar calendar;
    private String name;
    public WeatherInformation(double cloudiness, double humidity, double windSpeed, double maxTemp, double minTemp, String iconName, Calendar calendar, String name) {
        this.cloudiness = cloudiness;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.iconName = iconName;
        this.calendar = calendar;
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
