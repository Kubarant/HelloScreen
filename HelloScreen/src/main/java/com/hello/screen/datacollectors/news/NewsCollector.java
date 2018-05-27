package com.hello.screen.datacollectors.news;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.News;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.NewsChooserService;
import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsCollector implements DataCollector<News> {
    @Autowired
    private
    ProfileRepository repository;

    @Autowired
    private
    NewsChooserService chooserService;

    @Scheduled(fixedDelay = 60 * 1000 * 60, initialDelay = 2000)
    public List<News> collect() {
        Logger.info("Starting collecting news");
        Map<String, List<News>> categories = collectCategories();
        System.out.println(categories);
        repository.findAll()
                .doOnNext(profile -> profile.setNews(chooserService.choosePrefferedNews(categories, profile.getPrefferedCategories())))
                .subscribe(prof -> repository.save(prof)
                        .subscribe());
        Logger.info("Ending collecting news");

        return null;
    }


    private List<News> extractNewses(String url, String category) {
        UserAgent agent = new UserAgent();
        try {
            agent.visit(url);
        } catch (ResponseException e) {
            return Arrays.asList();
        }
        List<Element> elements = agent.doc.findEvery("<item>")
                .toList();

        return elements.stream()
                .map(el -> extractNews(el, category))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<News> extractNews(Element el, String category) {
        try {
            String title = el.findFirst("<title>")
                    .getText();
            String date = el.findFirst("<pubDate>")
                    .getText();
            String link = el.getText();
            LocalDateTime pubDate = LocalDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);

            Document description = badEncodedHtmlfixer(el.findFirst("<description>")
                    .innerHTML());
            String imagesrc = description.findFirst("<img")
                    .getAt("src");
            return Optional.ofNullable(new News(title, category, pubDate, "", link, imagesrc));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    private Document badEncodedHtmlfixer(String html) throws ResponseException {
        String fixed = html.replace("&lt;", "<")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&gt;", ">")
                .replace("&apos;", "'");

        return new UserAgent().openContent(fixed);
    }


    private Map<String, List<News>> collectCategories() {
        Map<String, List<News>> categoryNewsMap = new HashMap<>();
        List<News> mainNews = extractNewses("https://news.google.com/news/rss/?ned=pl_pl&hl=pl&gl=PL", "All");
        List<News> techNews = extractNewses("https://news.google.com/news/rss/headlines/section/topic/SCITECH.pl_pl/Nauka%20i%20technika?ned=pl_pl&hl=pl&gl=PL", "Tech");
        List<News> enterteimentNews = extractNewses("https://news.google.com/news/rss/headlines/section/topic/ENTERTAINMENT.pl_pl/Rozrywka?ned=pl_pl&hl=pl&gl=PL", "Fun");
        List<News> economyNews = extractNewses("https://news.google.com/news/rss/headlines/section/topic/BUSINESS.pl_pl/Gospodarka?ned=pl_pl&hl=pl&gl=PL", "Economy");
        List<News> sportNews = extractNewses("https://news.google.com/news/rss/headlines/section/topic/SPORTS.pl_pl/Sport?ned=pl_pl&hl=pl&gl=PL", "Sport");
        List<News> polandNews = extractNewses("https://news.google.com/news/rss/headlines/section/topic/NATION.pl_pl/Polska?ned=pl_pl&hl=pl&gl=PL", "Pol");
        List<News> localNews = extractNewses("https://news.google.com/news/rss/local/section/geo/Lubacz%C3%B3w,%20Polska/Lubacz%C3%B3w,%20Wojew%C3%B3dztwo%20podkarpackie?ned=pl_pl&hl=pl&gl=PL", "Local");
        categoryNewsMap.put("All", mainNews);
        categoryNewsMap.put("Tech", techNews);
        categoryNewsMap.put("Economy", economyNews);
        categoryNewsMap.put("Fun", enterteimentNews);
        categoryNewsMap.put("Sport", sportNews);
        categoryNewsMap.put("Pol", polandNews);
        categoryNewsMap.put("Local", localNews);
        return categoryNewsMap;
    }
}
