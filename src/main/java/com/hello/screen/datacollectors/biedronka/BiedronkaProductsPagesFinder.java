package com.hello.screen.datacollectors.biedronka;

import io.vavr.collection.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import static com.hello.screen.utils.DocumentDownloadUtil.downloadDocument;

@Service
public class BiedronkaProductsPagesFinder {

    public List<String> findProductPagesUrls(String url) {
        Document document = downloadDocument(url);
        return findProductPagesUrls(document).map(relative -> url + relative);
    }

    public List<String> findProductPagesUrls(Document document) {
        Elements urlElements = findUrlElements(document);
        return findProductPagesUrls(urlElements);
    }


    private List<String> findProductPagesUrls(Elements urlElements) {
        return List.ofAll(urlElements)
                .map(el -> el.attr("href"));
    }

    private Elements findUrlElements(Document document) {
        return document.select("#menuList > *:lt(3) a[href^=\"/pl/\"]");

    }
}
