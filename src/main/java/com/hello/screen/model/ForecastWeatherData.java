package com.hello.screen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document()
public class ForecastWeatherData {
    @Id
    private String id;
    private List<WeatherData> list;
    private LocalDateTime from;
    private LocalDateTime to;
    private LocalDateTime creation;


    public ForecastWeatherData() {
    }

    public ForecastWeatherData(List<WeatherData> list) {
        super();

        this.list = list;
        this.from = list.size() > 0 ? list.get(0)
                .getDate() : LocalDateTime.now();
        this.to = list.size() > 0 ? list.get(list.size() - 1)
                .getDate() : LocalDateTime.now();
        this.creation = LocalDateTime.now();


    }

    public ForecastWeatherData(List<WeatherData> list, LocalDateTime from, LocalDateTime to) {
        super();
        this.list = list;
        this.from = from;
        this.to = to;
    }

    public ForecastWeatherData(String id, List<WeatherData> list, LocalDateTime from, LocalDateTime to, LocalDateTime creation) {
        super();
        this.id = id;
        this.list = list;
        this.from = from;
        this.to = to;
    }


    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<WeatherData> getList() {
        return list;
    }

    public void setList(List<WeatherData> list) {
        this.list = list;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "ForecastWeatherData [id=" + id + ", list=" + list + ", from=" + from + ", to=" + to + "]";
    }


}
