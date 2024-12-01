package com.smoke.meteo_app.repository;

import com.smoke.meteo_app.entity.Meteo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoRepository extends MongoRepository<Meteo,String> {


     Meteo findByLatitudeAndLongitude(double latitude, double longitude);
     Meteo deleteByLatitudeAndLongitude(double latitude, double longitude);

}
