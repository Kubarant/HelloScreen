package com.hello.screen.datacollectors.weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import com.hello.screen.model.ForecastWeatherData;
import com.hello.screen.repository.ReactiveForecastWeatherRepository;
import com.hello.screen.repository.ReactiveWeatherRepository;

import reactor.core.publisher.Mono;


@EnableScheduling
@Configuration
public class WeatherDataCollector {
	
	@Autowired
	ReactiveWeatherRepository weatherRepository;
	
	@Autowired
	ReactiveForecastWeatherRepository forecastRepository;
	
	@Autowired
	WeatherDataMapper dataMapper;
	
	final String appKey="d5ad5b22d5ed61d52a07fbeea6ff1cbf";
	
	
	
	@Scheduled(fixedRate=1000*60*30, initialDelay=2000)
	public void collectCurrentWeather(){
		System.out.println("eyyys ");	
		String apiUrl="http://api.openweathermap.org/data/2.5/weather?q=Lubaczow&appid=";
		 Mono<String> responseBody =getResponseBody(apiUrl+appKey);
		
		 responseBody.map(response->dataMapper.currentWeatherFromJson(response)).filter(opData->opData.isPresent())
				 .map(Optional::get).subscribe(data->weatherRepository.save(data).subscribe());
		
		System.out.println("eyyy ");
	}
	@Scheduled(fixedRate=1000*60*30, initialDelay=2000)
	public void collectForecastWeather() {
		String apiUrl="http://api.openweathermap.org/data/2.5/forecast?q=Lubaczow&appid=";
		 Mono<String> responseBody =getResponseBody(apiUrl+appKey);
		 Mono<ForecastWeatherData> forecast = responseBody.map(dataMapper::forecastWeatherFromJson).filter(Optional::isPresent).map(Optional::get).map(ForecastWeatherData::new);
		 forecast.subscribe(forecastData-> forecastRepository.save(forecastData).subscribe(System.out::println));
		
	}
	
	public Mono<String> getResponseBody(String url){
		return WebClient.create(url).get()
			.exchange().block().body(BodyExtractors.toMono(String.class)).defaultIfEmpty("");
		
	}
	

		
		
	
	
	
	
	
	
}
