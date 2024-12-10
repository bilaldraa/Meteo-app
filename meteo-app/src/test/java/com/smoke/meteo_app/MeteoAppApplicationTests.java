package com.smoke.meteo_app;

import com.smoke.meteo_app.dto.MeteoDto;
import com.smoke.meteo_app.entity.Meteo;
import com.smoke.meteo_app.repository.MeteoRepository;
import com.smoke.meteo_app.service.MeteoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MeteoAppApplicationTests {


    @Mock
    MeteoRepository meteoRepository;

    @Mock
    Clock fixedClock;

    @InjectMocks
    @Autowired
    MeteoService meteoService;


    @BeforeEach
    public void setup() {
        Instant fixedInstant = Instant.parse("2024-12-05T10:00:00Z");
        // Configura el mock para devolver siempre un tiempo fijo
        Mockito.when(fixedClock.instant()).thenReturn(fixedInstant);
        Mockito.when(fixedClock.getZone()).thenReturn(ZoneId.systemDefault());
    }

    @Test
    public void fetchFromDataBaseTest() {

        Meteo expectedMeteo = new Meteo();

        expectedMeteo.setLatitude(12.00);
        expectedMeteo.setLongitude(20.00);
        expectedMeteo.setTemperature("30");
        expectedMeteo.setCreationDate(LocalDateTime.now().minusSeconds(30));

        Mockito.when(meteoRepository.findByLatitudeAndLongitude(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(expectedMeteo);

        MeteoDto actualMeteo = meteoService.getTemperatureService(1, 1);

        Assertions.assertEquals(expectedMeteo.getLatitude(), actualMeteo.getLatitude());
        Assertions.assertEquals(expectedMeteo.getLongitude(), actualMeteo.getLongitude());
        Assertions.assertEquals(expectedMeteo.getTemperature(), actualMeteo.getTemperature());

    }

    @Test
    public void dateIsOneMinuteOlder() {

        LocalDateTime fixedNow = LocalDateTime.now(fixedClock);


        // comprobamos que cuando no ha pasado un minuto, devuelve false
        for (int i = 1; i <= 60; i++) {
            Assertions.assertFalse(meteoService.dateIsOneMinuteOlder(fixedNow.minusSeconds(i)));
        }

        // ha pasado un minuto, por lo que debería devolver true
        Assertions.assertTrue(meteoService.dateIsOneMinuteOlder(fixedNow.minusSeconds(61)));

        // han pasado 10 minutos
        Assertions.assertTrue(meteoService.dateIsOneMinuteOlder(fixedNow.minusMinutes(10)));


    }

}
