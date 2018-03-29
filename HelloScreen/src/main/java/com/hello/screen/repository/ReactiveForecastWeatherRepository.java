package com.hello.screen.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.hello.screen.model.ForecastWeatherData;

public interface ReactiveForecastWeatherRepository extends ReactiveCrudRepository<ForecastWeatherData, String>{
	   
	   


}
