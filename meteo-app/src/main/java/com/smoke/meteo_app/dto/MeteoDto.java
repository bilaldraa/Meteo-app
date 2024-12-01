package com.smoke.meteo_app.dto;


public class MeteoDto {

    private double latitude;
    private double longitude;
    private String temperature;


    public MeteoDto(){

    }
    public MeteoDto(double latitude, double longitude, String temperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
