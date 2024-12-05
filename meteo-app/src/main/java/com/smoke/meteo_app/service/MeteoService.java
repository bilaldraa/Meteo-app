package com.smoke.meteo_app.service;


import com.smoke.meteo_app.dto.MeteoDto;
import com.smoke.meteo_app.entity.Meteo;
import com.smoke.meteo_app.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class MeteoService {

    public static final String METEO_BASE_URI = "https://api.open-meteo.com/v1";


    @Autowired
    private MeteoRepository meteoRepository;


    public MeteoService() {

    }


    public MeteoDto getTemperatureService(double lat, double lon) {

        try {

            // first we try find in database
            Meteo meteo = meteoRepository.findByLatitudeAndLongitude(lat, lon);

            // we found it and was created less than 1 minute before
            if (meteo != null && !dateIsOneMinuteOlder(meteo.getCreationDate())) {
                MeteoDto meteoDto = new MeteoDto();
                meteoDto.setTemperature(meteo.getTemperature());
                meteoDto.setLongitude(meteo.getLongitude());
                meteoDto.setLatitude(meteo.getLatitude());

                meteo.setCreationDate(LocalDateTime.now());
                meteoRepository.save(meteo);

                return meteoDto;
            } else {
                return fetchDataFromMeteoApi(lat, lon);
            }

        } catch (Exception e) {
            System.err.println(e);
            return new MeteoDto();
        }

    }

    public MeteoDto fetchDataFromMeteoApi(double lat, double lon) {

        RestClient meteoRestClient  = RestClient.builder().baseUrl(METEO_BASE_URI).build();

        Map response = meteoRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("hourly", "temperature_2m")
                        .queryParam("timezone", "Europe/Berlin")
                        .queryParam("forecast_days", "1")
                        .build())
                .retrieve()
                .body(Map.class);

        MeteoDto meteoDtoResponse = mapResponseToMeteoDto(response);


        saveMeteo(meteoDtoResponse);

        return meteoDtoResponse;

    }

    public MeteoDto mapResponseToMeteoDto(Map response) {

        List<Double> temperatureList = (List<Double>) ((LinkedHashMap) response.get("hourly")).get("temperature_2m");

        double latitude = (double) response.get("latitude");
        double longitude = (double) response.get("longitude");
        String averageTemperature = getAverageTemperature(temperatureList);

        MeteoDto meteoDto = new MeteoDto();

        meteoDto.setLatitude(latitude);
        meteoDto.setLongitude(longitude);
        meteoDto.setTemperature(averageTemperature);


        return meteoDto;
    }


    public String getAverageTemperature(List<Double> temperatureList) {

        double temperatureSum = 0;
        for (Double temperature : temperatureList) {
            temperatureSum += temperature;
        }

        temperatureSum = temperatureSum / temperatureList.size();

        return String.valueOf(Math.round(temperatureSum));
    }

    public void saveMeteo(MeteoDto meteoDto) {


        Meteo meteo =   meteoRepository.findByLatitudeAndLongitude(meteoDto.getLatitude(),meteoDto.getLongitude());

        if(meteo == null){
            meteo = new Meteo();
            meteo.setLatitude(meteoDto.getLatitude());
            meteo.setLongitude(meteoDto.getLongitude());
            meteo.setTemperature(meteoDto.getTemperature());
        }

        meteo.setCreationDate(LocalDateTime.now());



        meteoRepository.save(meteo);
    }

    public boolean dateIsOneMinuteOlder(LocalDateTime savedDate) {
        return savedDate.plusMinutes(1).isBefore(LocalDateTime.now());
    }

    public Meteo deleteMeteo(double lat, double lon){
        return meteoRepository.deleteByLatitudeAndLongitude(lat,lon);
    }
}
