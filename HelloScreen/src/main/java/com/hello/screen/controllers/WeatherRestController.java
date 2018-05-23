package com.hello.screen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.model.WeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import com.hello.screen.repository.ReactiveWeatherRepository;

import reactor.core.publisher.Mono;

@RestController
public class WeatherRestController {
	
	@Autowired
	ReactiveWeatherRepository weatherRepository;
	
	@Autowired
	ReactiveForecastWeatherRepository forecastRepository;
	
	@Autowired
	MongoTemplate template;
	
	@GetMapping("/sun")
	public Mono<WeatherData> currentWeather(){
		Query query = new Query();
	    query.limit(1);
	    query.with(new Sort(Sort.Direction.DESC, "creation"));
	    List<WeatherData> find = template.find(query, WeatherData.class);
	    System.out.println("su n is shinig" + find);
	    
	    return Mono.just(find.get(0));
	}
	@GetMapping("/forecast")
	public Mono<ForecastWeatherData> forecastWeather(){
		Query query = new Query();
	    query.limit(1);
	    query.with(new Sort(Sort.Direction.DESC, "creation"));
	    List<ForecastWeatherData> find = template.find(query, ForecastWeatherData.class);
	    System.out.println("Start  "+find+"    Stop");
	    System.out.println("apapap \n\n"+find.get(0));
	    
	    return Mono.just(find.get(0));
	}

}
