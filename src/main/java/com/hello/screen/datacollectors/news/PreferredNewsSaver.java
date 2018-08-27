package com.hello.screen.datacollectors.news;

import com.hello.screen.model.News;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.NewsChooserService;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class PreferredNewsSaver {

    private ProfileRepository repository;
    private NewsChooserService chooserService;

    @Autowired
    public PreferredNewsSaver(ProfileRepository repository, NewsChooserService chooserService) {
        this.repository = repository;
        this.chooserService = chooserService;
    }


    public void matchNewsForEachProfile(List<News> news) {
        repository.findAll()
                .doOnNext(profile -> profile.setNews(fitNewsToProfile(news, profile)))
                .flatMap(prof -> repository.save(prof))
                .subscribe();
    }

    private java.util.List<News> fitNewsToProfile(List<News> news, Profile profile) {
        Map<String, java.util.List<News>> categoryNews = news.collect(groupingBy(News::getCategory));
        return chooserService.choosePreferredNews(categoryNews, profile.getPreferredCategories());
    }
}
