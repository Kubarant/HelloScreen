package com.hello.screen.services;

import com.hello.screen.model.CategoryPreferences;
import com.hello.screen.model.News;
import com.hello.screen.utils.ListUtil;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class NewsChooserService {

    private static final int MAXIMUM_NEWS_AMOUNT = 50;

    public List<News> choosePrefferedNews(Map<String, List<News>> news,
                                          List<CategoryPreferences> prefferedCategoriess) {

        Map<String, Integer> categoryNewsSize = convertToCategorySizeMap(news);
        Map<String, Integer> newsAmountInGivenCategory = convertToNewsAmountInCategoryMap(prefferedCategoriess, categoryNewsSize);
        List<News> prefferedNews = chooseNews(news, newsAmountInGivenCategory);
        Collections.shuffle(prefferedNews);
        return prefferedNews;
    }

    private List<News> chooseNews(Map<String, List<News>> news, Map<String, Integer> newsInCategoryAmount) {
        return newsInCategoryAmount.entrySet()
                .stream()
                .map(ent -> chooseNewsUsingOnNewsInCategoryEntry(ent, news))
                .reduce(ListUtil::combine)
                .get();
    }

    private Map<String, Integer> convertToNewsAmountInCategoryMap(List<CategoryPreferences> preferences,
                                                                  Map<String, Integer> categoryNewsSize) {
        return preferences.stream()
                .map(preference -> newsAmountMap(preference, categoryNewsSize))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<String, Integer> convertToCategorySizeMap(Map<String, List<News>> news) {
        return news.entrySet()
                .stream()
                .map(ent -> new AbstractMap.SimpleEntry<>(ent.getKey(), ent.getValue()
                        .size()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private List<News> chooseNewsUsingOnNewsInCategoryEntry(Entry<String, Integer> newsInCategory,
                                                            Map<String, List<News>> news) {
        return news.get(newsInCategory.getKey())
                .subList(0,
                        newsInCategory.getValue() > 0 ? newsInCategory.getValue() : 0);
    }

    private Entry<String, Integer> newsAmountMap(CategoryPreferences preference,
                                                 Map<String, Integer> categoryNewsSize) {

        Integer newsSize = categoryNewsSize.get(preference.getLabel());
        double newsAmountDouble = (preference.getValue() / 100.0d) * (double) MAXIMUM_NEWS_AMOUNT;

        int newsAmount = newsSize > newsAmountDouble ? (int) Math.floor(newsAmountDouble) : newsSize;
        return new AbstractMap.SimpleEntry<>(preference.getLabel(), newsAmount);
    }

}
