package com.hello.screen;

import com.hello.screen.datacollectors.biedronka.BiedronkaImageDownloader;
import com.hello.screen.datacollectors.biedronka.BiedronkaProductParser;
import com.hello.screen.model.Product;
import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class BiedronkaProductParserTest {
    private BiedronkaProductParser parser;
    private Document validDoc;


    @Before
    public void setUp() throws Exception {
        InputStream newsInput = getClass().getClassLoader()
                .getResourceAsStream("biedronka\\productpage.html");
        validDoc = Jsoup.parse(newsInput, "UTF-8", "", Parser.htmlParser());
        String imagesPath = getClass().getClassLoader()
                .getResource("biedronka")
                .toString() + "/images/";
        parser = new BiedronkaProductParser(new BiedronkaImageDownloader(imagesPath), false);
    }

    @Test
    public void findOfferPagesInValidDoc() {
        List<Product> products = parser.receiveProductsPage(validDoc);
        System.out.println("products.size() = " + products.size());
        assertEquals(7, products.size());
    }

    @Test
    public void findOfferPagesInEmptyDoc() {
        Document emptyDocument = Jsoup.parse("");
        List<Product> emptyListProducts = parser.receiveProductsPage(emptyDocument);
        assertEquals(0, emptyListProducts.size());
    }

}
