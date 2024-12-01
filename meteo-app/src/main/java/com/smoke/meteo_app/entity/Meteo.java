package com.smoke.meteo_app.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Meteo {

    public Meteo() {
    }

    public Meteo(String id, double latitude, double longitude, String temperature, LocalDateTime creationDate) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.creationDate = creationDate;
    }

    @Id
    private String id;

    private double latitude;

    private double longitude;

    private String temperature;

    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Meteo{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", creationDate=" + creationDate +
                '}';
    }


}
