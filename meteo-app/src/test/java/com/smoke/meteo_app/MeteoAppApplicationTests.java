package com.smoke.meteo_app;

import com.smoke.meteo_app.dto.MeteoDto;
import com.smoke.meteo_app.entity.Meteo;
import com.smoke.meteo_app.repository.MeteoRepository;
import com.smoke.meteo_app.service.MeteoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ExecutableAggregationOperation;

import java.time.LocalDateTime;

@SpringBootTest
class MeteoAppApplicationTests {


	@Mock
	MeteoRepository meteoRepository;

	@Autowired
	MeteoService meteoService;


	@Test
	void contextLoads() {
	}

	@Test
	public void fetchFromDataBaseTest(){

		Meteo expectedMeteo = new Meteo();

		expectedMeteo.setLatitude(12.00);
		expectedMeteo.setLongitude(20.00);
		expectedMeteo.setTemperature("30");
		expectedMeteo.setCreationDate(LocalDateTime.now().minusSeconds(30));


		Mockito.when(meteoRepository.findByLatitudeAndLongitude(Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedMeteo);

		meteoService.fetchDataFromMeteoApi(1,1);
	}


}
