package com.hello.screen;

import com.hello.screen.datacollectors.weather.WeatherDataMapper;
import com.hello.screen.model.WeatherData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
public class JsonWeatherToObjectMappingTest {

    private WeatherDataMapper dataMapper;
    private String currentWeatherJson;
    private String forecastWeatherJson;

    @Before
    public void setUp() throws Exception {
        dataMapper = new WeatherDataMapper();

        byte[] currentData = Files
                .readAllBytes(Paths.get(this.getClass()
                        .getClassLoader()
                        .getResource("current.json")
                        .toURI()));
        byte[] forecastData = Files
                .readAllBytes(Paths.get(this.getClass()
                        .getClassLoader()
                        .getResource("forecast.json")
                        .toURI()));

        currentWeatherJson = new String(currentData, Charset.forName("UTF-8"));
        forecastWeatherJson = new String(forecastData, Charset.forName("UTF-8"));
    }

    @Test
    public void currentWeatherMappingTest() {
        WeatherData weatherData = dataMapper.currentWeatherFromJson(currentWeatherJson)
                .get();

        WeatherData properData = new WeatherData(277.15f, 1012, 55, 10000, 3, LocalDateTime.parse("2018-03-25T19:00"),
                LocalDateTime.parse("2018-03-25T06:18:13"), LocalDateTime.parse("2018-03-25T18:49:18"), LocalDateTime.now(), "Clear", 0.0f,
                0.0f);

        assertEquals(weatherData, properData);

        boolean present = dataMapper.currentWeatherFromJson("")
                .isPresent();
        assertFalse(present);

    }

    @Test
    public void forecastWeatherMappingTest() {
        ArrayList<WeatherData> weatherData = dataMapper.forecastWeatherFromJson(forecastWeatherJson)
                .get();
        assertEquals(weatherData.size(), 40);

        boolean present = dataMapper.forecastWeatherFromJson("")
                .isPresent();
        assertFalse(present);

    }

}
