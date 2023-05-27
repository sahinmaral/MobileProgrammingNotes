package com.example.weatherapp;

public class CustomResponse {
    WeatherResponse[] list;
}

class WeatherResponse{
    WeatherResponseCloudObject clouds;
    WeatherResponseMainObject main;
    WeatherResponseIconObject[] weather;
    WeatherResponseWindSpeedObject wind;
    int dt;
}

class WeatherResponseCloudObject {
    double all;
}
class WeatherResponseMainObject{
    double temp_min;
    double temp_max;
    double humidity;
    double pressure;
}

class WeatherResponseWindSpeedObject{
    double speed;
    double deg;
    double gust;
}

class WeatherResponseIconObject{
    String id;
    String main;
    String icon;
}
