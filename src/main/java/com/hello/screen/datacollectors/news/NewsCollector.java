package com.hello.screen.datacollectors.news;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.News;
import com.hello.screen.repository.NewsRepository;
import com.hello.screen.services.ChecksumService;
import io.vavr.collection.List;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
class NewsCollector implements DataCollector<News> {
    private ChecksumService checksumService;
    private NewsRepository newsRepository;
    private NewsProperties newsUrls;
    private PreferredNewsSaver saver;
    private NewsParser newsParser;

    @Autowired
    public NewsCollector(ChecksumService checksumService, NewsRepository newsRepository, NewsProperties newsUrls, PreferredNewsSaver saver, NewsParser newsParser) {
        this.checksumService = checksumService;
        this.newsRepository = newsRepository;
        this.newsUrls = newsUrls;
        this.saver = saver;
        this.newsParser = newsParser;
    }

    @Scheduled(fixedDelay = 60 * 1000 * 60, initialDelay = 2000)
    public java.util.List<News> collect() {
        Logger.info("Start collecting news from {newsUrls}", newsUrls);

        List<News> news = collectCategories()
                .reduce(List::appendAll);
        Logger.debug("Collected news     {news}", news);

        checksumService.replaceObjectsIfNotAlreadyStored(news.asJava(), newsRepository)
                .subscribe();
        saver.matchNewsForEachProfile(news);
        Logger.info("Ending collecting news");

        return news.asJava();
    }


    private List<List<News>> collectCategories() {
        List<News> all = newsParser.parseNews(newsUrls.mainUrl, "All");
        List<News> tech = newsParser.parseNews(newsUrls.techUrl, "Tech");
        List<News> fun = newsParser.parseNews(newsUrls.entertainmentUrl, "Fun");
        List<News> economy = newsParser.parseNews(newsUrls.economyUrl, "Economy");
        List<News> sport = newsParser.parseNews(newsUrls.sportUrl, "Sport");
        List<News> pol = newsParser.parseNews(newsUrls.countryUrl, "Pol");
        List<News> local = newsParser.parseNews(newsUrls.localUrl, "Local");

        return List.of(all, tech, fun, economy, sport, pol, local);
    }
}
