package com.hello.screen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class WeatherData {

    private float temp;
    private int pressure;
    private int humidity;
    private int visibility;
    private long windSpeed;
    private LocalDateTime date;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private LocalDateTime creation;
    private String weather;
    private float snow;
    private float rain;

    private @Id
    String id;

    public WeatherData(float temp, int pressure, int humidity, int visibility, long windSpeed, LocalDateTime date,
                       LocalDateTime sunrise, LocalDateTime sunset, LocalDateTime creation, String weather, float snow,
                       float rain) {
        super();
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.creation = creation;
        this.weather = weather;
        this.snow = snow;
        this.rain = rain;
    }

    public WeatherData(float temp, int pressure, int humidity, int visibility, long windSpeed, LocalDateTime date,
                       LocalDateTime sunrise, LocalDateTime sunset, LocalDateTime creation, String weather, float snow, float rain,
                       String id) {
        super();
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.creation = creation;
        this.weather = weather;
        this.snow = snow;
        this.rain = rain;
        this.id = id;
    }

    public WeatherData() {
        super();
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public long getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(long windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalDateTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalDateTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalDateTime sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        return "WeatherData [temp=" + temp + ", pressure=" + pressure + ", humidity=" + humidity + ", visibility="
                + visibility + ", windSpeed=" + windSpeed + ", date=" + date + ", sunrise=" + sunrise + ", sunset="
                + sunset + ", weather=" + weather + ", snow=" + snow + ", rain=" + rain + ", id=" + id + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + humidity;
        result = prime * result + pressure;
        result = prime * result + Float.floatToIntBits(rain);
        result = prime * result + Float.floatToIntBits(snow);
        result = prime * result + ((sunrise == null) ? 0 : sunrise.hashCode());
        result = prime * result + ((sunset == null) ? 0 : sunset.hashCode());
        result = prime * result + Float.floatToIntBits(temp);
        result = prime * result + visibility;
        result = prime * result + ((weather == null) ? 0 : weather.hashCode());
        result = prime * result + (int) (windSpeed ^ (windSpeed >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeatherData other = (WeatherData) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (humidity != other.humidity)
            return false;
        if (pressure != other.pressure)
            return false;
        if (Float.floatToIntBits(rain) != Float.floatToIntBits(other.rain))
            return false;
        if (Float.floatToIntBits(snow) != Float.floatToIntBits(other.snow))
            return false;
        if (sunrise == null) {
            if (other.sunrise != null)
                return false;
        } else if (!sunrise.equals(other.sunrise))
            return false;
        if (sunset == null) {
            if (other.sunset != null)
                return false;
        } else if (!sunset.equals(other.sunset))
            return false;
        if (Float.floatToIntBits(temp) != Float.floatToIntBits(other.temp))
            return false;
        if (visibility != other.visibility)
            return false;
        if (weather == null) {
            if (other.weather != null)
                return false;
        } else if (!weather.equals(other.weather))
            return false;
        return windSpeed == other.windSpeed;
    }

}
