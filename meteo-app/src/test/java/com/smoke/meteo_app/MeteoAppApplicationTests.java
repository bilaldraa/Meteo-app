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

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
@SpringBootTest

class MeteoAppApplicationTests {


	@Mock
	MeteoRepository meteoRepository;

	@InjectMocks
	@Autowired
	MeteoService meteoService;


	@Test
	public void fetchFromDataBaseTest(){

		Meteo expectedMeteo = new Meteo();

		expectedMeteo.setLatitude(12.00);
		expectedMeteo.setLongitude(20.00);
		expectedMeteo.setTemperature("30");
		expectedMeteo.setCreationDate(LocalDateTime.now().minusSeconds(30));

		Mockito.when(meteoRepository.findByLatitudeAndLongitude(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(expectedMeteo);

		MeteoDto actualMeteo = meteoService.getTemperatureService(1,1);

		Assertions.assertEquals(expectedMeteo.getLatitude(),actualMeteo.getLatitude());
		Assertions.assertEquals(expectedMeteo.getLongitude(), actualMeteo.getLongitude());
		Assertions.assertEquals(expectedMeteo.getTemperature(), actualMeteo.getTemperature());

	}


}
