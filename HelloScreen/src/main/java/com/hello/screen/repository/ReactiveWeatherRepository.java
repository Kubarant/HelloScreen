package com.hello.screen.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.hello.screen.model.WeatherData;

public interface ReactiveWeatherRepository extends ReactiveCrudRepository<WeatherData, String>{
	   
	   


}
