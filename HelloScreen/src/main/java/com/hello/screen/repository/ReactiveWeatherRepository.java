package com.hello.screen.repository;

import com.hello.screen.model.WeatherData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveWeatherRepository extends ReactiveCrudRepository<WeatherData, String>{
	   
	   

}
