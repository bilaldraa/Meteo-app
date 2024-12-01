package com.smoke.meteo_app.controller;

import com.smoke.meteo_app.service.MeteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meteo")
public class MeteoController {

    @Autowired
    private MeteoService meteoService;

    @GetMapping("/get")
    public String getMeteo(@RequestParam double lat, @RequestParam double lon){
        System.out.println("llamo a servicio meteoService");
            return meteoService.getTemperatureService(lat,lon).toString();
    }

    @DeleteMapping("/delete")
    public String deleteMeteo (@RequestParam double lat, @RequestParam double lon){
     return meteoService.deleteMeteo(lat,lon) != null ? "Borrado con exito": "No se han encontrado registros" ;
    }


}
