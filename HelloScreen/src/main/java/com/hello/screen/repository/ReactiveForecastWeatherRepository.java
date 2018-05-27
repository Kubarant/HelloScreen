package com.hello.screen.repository;

import com.hello.screen.model.ForecastWeatherData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveForecastWeatherRepository extends ReactiveCrudRepository<ForecastWeatherData, String>{
	   
	   


}
