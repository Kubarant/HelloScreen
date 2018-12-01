package com.hello.screen.datacollectors.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.screen.model.WeatherData;
import com.hello.screen.utils.DateUtil;
import com.hello.screen.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class WeatherDataMapper {


    private static WeatherData receiveWeatherDataFromNode(JsonNode node) {

        String weather = node.path("weather")
                .get(0)
                .path("main")
                .asText();
        JsonNode tnode = node.path("main");

        float temp = tnode.path("temp")
                .floatValue();
        int pressure = tnode.path("pressure")
                .asInt();
        int humidity = tnode.path("humidity")
                .asInt();
        int visibility = node.path("visibility")
                .asInt();
        long windSpeed = node.path("wind")
                .path("speed")
                .asLong();
        float snow = node.at("/snow/3h")
                .isDouble() ? node.at("/snow/3h")
                .floatValue() : 0;

        float rain = node.at("/rain/3h")
                .isDouble() ? node.at("/rain/3h")
                .floatValue() : 0;
        LocalDateTime date = DateUtil.dateOfUnixTimestamp(node.path("dt")
                .asLong());
        LocalDateTime sunrise = DateUtil.dateOfUnixTimestamp(node.path("sys")
                .path("sunrise")
                .asLong());
        LocalDateTime sunset = DateUtil.dateOfUnixTimestamp(node.path("sys")
                .path("sunset")
                .asLong());
        return new WeatherData(temp, pressure, humidity, visibility, windSpeed, date, sunrise, sunset, LocalDateTime.now(), weather, snow, rain);
    }

    public static ArrayList<WeatherData> receiveWeatherDataFromNodes(ArrayList<JsonNode> nodes) {
        ArrayList<WeatherData> list = new ArrayList<>();
        for (JsonNode node : nodes) {
            list.add(receiveWeatherDataFromNode(node));
        }
        return list;
    }

    public Optional<WeatherData> jsonToWeatherData(String json) {
        return stringToJsonNode(json).map(WeatherDataMapper::receiveWeatherDataFromNode);

    }

    public Optional<JsonNode> stringToJsonNode(String json) {
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
        return jsonToWeatherData(json);
    }

    public Optional<ArrayList<WeatherData>> forecastWeatherFromJson(String json) {
        Optional<JsonNode> node = stringToJsonNode(json);
        return node.map(n -> n.path("list"))
                .map(JsonUtil::mapToArraylist)
                .map(WeatherDataMapper::receiveWeatherDataFromNodes);

    }


}
