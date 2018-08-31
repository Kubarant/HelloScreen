package com.hello.screen.utils;

import io.vavr.control.Try;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentDownloadUtil {

    public static Document downloadDocument(String url) {
        Connection connection = Jsoup.connect(url);
        Document emptyDoc = Jsoup.parse("");
        Try<Document> downloadDoc = Try.of(connection::get);
        return downloadDoc.getOrElse(emptyDoc);
    }


}
