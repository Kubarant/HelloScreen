package com.hello.screen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.hello.screen.model.WeatherData;

@RunWith(SpringRunner.class)
public class JsonWeatherToObjectMappingTest {

	WeatherDataMapper dataMapper;
	String currentWeatherJson;
	String forecastWeatherJson;

	@Before
	public void setUp() throws Exception {
		dataMapper = new WeatherDataMapper();

		byte[] currentData = Files
				.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("current.json").toURI()));
		byte[] forecastData = Files
				.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("forecast.json").toURI()));

		currentWeatherJson = new String(currentData);
		forecastWeatherJson = new String(forecastData);

	}

	@Test
	public void currentWeatherMappingTest() throws IOException {
		WeatherData weatherData = dataMapper.currentWeatherFromJson(currentWeatherJson).get();

		WeatherData properData = new WeatherData(277.15f, 1012, 55, 10000, 3, LocalDateTime.parse("2018-03-25T19:00"),
				LocalDateTime.parse("2018-03-25T06:18:13"), LocalDateTime.parse("2018-03-25T18:49:18"), "Clear", 0.0f,
				0.0f);

		assertEquals(weatherData, properData);

		boolean present = dataMapper.currentWeatherFromJson("").isPresent();
		assertFalse(present);

	}

	@Test
	public void forecastWeatherMappingTest() throws IOException {
		ArrayList<WeatherData> weatherData = dataMapper.forecastWeatherFromJson(forecastWeatherJson).get();
		assertEquals(weatherData.size(), 40);

		boolean present = dataMapper.forecastWeatherFromJson("").isPresent();
		assertFalse(present);

	}

}
