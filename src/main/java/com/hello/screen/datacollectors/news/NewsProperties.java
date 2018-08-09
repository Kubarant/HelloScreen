package com.hello.screen.datacollectors.news;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "news")
public class NewsProperties {

    @Value("${news.mainUrl}")
    String mainUrl;
    @Value("${news.techUrl}")
    String techUrl;
    @Value("${news.entertainmentUrl}")
    String entertainmentUrl;
    @Value("${news.economyUrl}")
    String economyUrl;
    @Value("${news.sportUrl}")
    String sportUrl;
    @Value("${news.localUrl}")
    String localUrl;
    @Value("${news.countryUrl}")
    String countryUrl;


    @Override
    public String toString() {
        return "NewsProperties{" +
                "mainUrl='" + mainUrl + '\'' +
                ", techUrl='" + techUrl + '\'' +
                ", entertainmentUrl='" + entertainmentUrl + '\'' +
                ", economyUrl='" + economyUrl + '\'' +
                ", sportUrl='" + sportUrl + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", countryUrl='" + countryUrl + '\'' +
                '}';
    }
}
