package com.hello.screen.datacollectors.biedronka;

import io.vavr.collection.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.hello.screen.utils.DocumentDownloadUtil.downloadDocument;

public class BiedronkaProductsPagesFinder {

    public List<String> findProductPagesUris(String url) {
        Document document = downloadDocument(url);
        return findProductPagesUris(document).map(relative -> url + relative);
    }

    public List<String> findProductPagesUris(Document document) {
        Elements urlElements = findUrlElements(document);
        return findProductPagesUris(urlElements);
    }


    private List<String> findProductPagesUris(Elements urlElements) {
        return List.ofAll(urlElements)
                .map(el -> el.attr("href"));
    }

    private Elements findUrlElements(Document document) {
        return document.select("#menuList > *:lt(3) a[href^=\"/pl/\"]");

    }
}
