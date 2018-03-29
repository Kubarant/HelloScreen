package com.hello.screen;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.screen.model.WeatherData;

@Service
public class WeatherDataMapper {
	
	
		public static WeatherData recieveWeatherDataFromNode(JsonNode node){
			
			String weather = node.path("weather").get(0).path("main").asText();
			JsonNode tnode = node.path("main");
		
			float temp= tnode.path("temp").floatValue();
			int pressure= tnode.path("pressure").asInt();
			int humidity= tnode.path("humidity").asInt();
			int visibility= node.path("visibility").asInt();
			long windSpeed = node.path("wind").path("speed").asLong();
			float snow = node.at("/snow/3h").isDouble()?node.at("/snow/3h").floatValue():0;
			
			float rain = node.at("/rain/3h").isFloat()?node.at("/rain/3h").floatValue():0;
			LocalDateTime date= DateUtil.dateOfUnixTimestamp(node.path("dt").asLong());
			LocalDateTime sunrise= DateUtil.dateOfUnixTimestamp(node.path("sys").path("sunrise").asLong());
			LocalDateTime sunset= DateUtil.dateOfUnixTimestamp(node.path("sys").path("sunset").asLong());
			return new WeatherData(temp, pressure, humidity, visibility, windSpeed, date, sunrise, sunset, weather, snow, rain);
		}
		
		public static ArrayList<WeatherData> recieveWeatherDataFromNodes(ArrayList<JsonNode> nodes){
			 ArrayList<WeatherData> list = new ArrayList<>();
			for (int i = 0; i < nodes.size(); i++) {
				list.add(recieveWeatherDataFromNode(nodes.get(i)));
			}
			return list;
		}
		
		public Optional<WeatherData> jsonToWeatherData(String json){
			Optional<WeatherData> node = stringToJsonNode(json).map(WeatherDataMapper::recieveWeatherDataFromNode);;
			return node;
			
		}
		public Optional<JsonNode> stringToJsonNode(String json){
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = null;
			try {
				node = objectMapper.readTree(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Optional.ofNullable(node);
			
		}
		
		
		public Optional<WeatherData> currentWeatherFromJson(String json) {
			Optional<WeatherData> weatherData = jsonToWeatherData(json);
			return weatherData;
		}
		
		public Optional<ArrayList<WeatherData>> forecastWeatherFromJson(String json) {
			Optional<JsonNode> node = stringToJsonNode(json);
			Optional<ArrayList<WeatherData>> weatherData = node.map(n-> n.path("list")).map(JsonUtil::mapToArraylist).map(list->recieveWeatherDataFromNodes(list));
			return weatherData;
			
		}
		
		

}
