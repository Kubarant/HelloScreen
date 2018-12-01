package com.hello.screen.datacollectors.news;

import com.hello.screen.model.News;
import com.hello.screen.utils.HtmlCharsRemover;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NewsParser {

    public List<News> parseNews(String url, String category) {
        return Try.of(() -> parseNews(downloadXmlDocument(url), category))
                .getOrElse(List::empty);
    }

    public List<News> parseNews(Document document, String category) {
        return List.ofAll(selectNewsElements(document))
                .map(this::mapToNews)
                .map(news -> news.category(category));
    }

    private Elements selectNewsElements(Document document) {
        return document.select("channel>item");
    }

    private Document downloadXmlDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .get();
    }

    private News mapToNews(Element el) {
        String title = parseFromPath(el, "title");
        String link = parseFromPath(el, "link");

        String date = parseFromPath(el, "pubDate");
        LocalDateTime pubDate = LocalDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
        String imageLink = parseImageLink(el);

        Document description = getDescription(el);
        String desc = parseFromPath(description, "a");

        return new News(title, "", pubDate, desc, link, imageLink);
    }

    private Document getDescription(Element el) {
        String description = el.select("description")
                .html();
        Document descriptionDoc = Jsoup.parseBodyFragment(HtmlCharsRemover.replaceEntities(description));
        descriptionDoc.outputSettings()
                .escapeMode(Entities.EscapeMode.xhtml);
        return descriptionDoc;
    }

    private String parseImageLink(Element description) {
        return description.select("media|content")
                .attr("url");
    }

    private String parseFromPath(Element el, String title) {
        return el.select(title)
                .text();
    }
}
