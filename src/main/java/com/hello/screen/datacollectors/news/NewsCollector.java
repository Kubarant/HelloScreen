package com.hello.screen.datacollectors.news;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.News;
import com.hello.screen.repository.NewsRepository;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.ChecksumService;
import com.hello.screen.services.NewsChooserService;
import io.vavr.collection.List;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewsCollector implements DataCollector<News> {
    @Autowired
    ChecksumService checksumService;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NewsProperties newsUrls;
    @Autowired
    private
    ProfileRepository repository;
    @Autowired
    private
    NewsChooserService chooserService;
    @Autowired
    private NewsParser newsParser;

    @Scheduled(fixedDelay = 60 * 1000 * 60, initialDelay = 2000)
    public java.util.List<News> collect() {
        Logger.info("Collecting from {newsUrls}", newsUrls);
        Logger.info("Starting collecting news");
        List<News> news = collectCategories()
                .reduce((a, b) -> a.appendAll(b));
        Logger.debug("Collected news     {news}", news);

        checksumService.replaceObjectsIfNotAlreadyStored(news.asJava(), newsRepository)
                .subscribe();
        savePreferredNewsForEachUsers(news);
        Logger.info("Ending collecting news");

        return null;
    }

    private void savePreferredNewsForEachUsers(List<News> news) {
        repository.findAll()
                .doOnNext(profile -> profile.setNews(chooserService.choosePreferredNews(news.collect(groupingBy(News::getCategory)), profile.getPrefferedCategories())))
                .flatMap(prof -> repository.save(prof))
                .subscribe();
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
