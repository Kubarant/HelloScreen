package com.hello.screen.datacollectors.news;

import com.hello.screen.model.News;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.NewsRepository;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.NewsChooserService;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class PreferredNewsSaver {

    private final ProfileRepository repository;
    private final NewsChooserService chooserService;
    private final NewsRepository newsRepository;

    @Autowired
    public PreferredNewsSaver(ProfileRepository repository, NewsChooserService chooserService, NewsRepository newsRepository) {
        this.repository = repository;
        this.chooserService = chooserService;
        this.newsRepository = newsRepository;
    }

    public void matchNewsForEachProfile(List<News> news) {
        repository.findAll()
                .doOnNext(profile -> profile.setNews(fitNewsToProfile(news, profile)))
                .flatMap(repository::save)
                .subscribe();
    }

    public java.util.List<News> fitNewsToProfile(Profile profile) {
        java.util.List<News> news = newsRepository.findAll()
                .collectList()
                .block();
        return fitNewsToProfile(List.ofAll(news), profile);
    }

    private java.util.List<News> fitNewsToProfile(List<News> news, Profile profile) {
        if (news.isEmpty())
            return Collections.emptyList();

        Map<String, java.util.List<News>> categoryNews = news.collect(groupingBy(News::getCategory));
        return chooserService.choosePreferredNews(categoryNews, profile.getPreferredCategories());
    }

}
