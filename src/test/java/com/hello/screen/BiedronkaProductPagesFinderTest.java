package com.hello.screen;

import com.hello.screen.datacollectors.biedronka.BiedronkaProductsPagesFinder;
import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class BiedronkaProductPagesFinderTest {
    private BiedronkaProductsPagesFinder finder;
    private Document validDoc;

    @Before
    public void setUp() throws Exception {
        InputStream newsInput = getClass().getClassLoader()
                .getResourceAsStream("biedronka\\menu.html");
        validDoc = Jsoup.parse(newsInput, "UTF-8", "", Parser.htmlParser());
        finder = new BiedronkaProductsPagesFinder();
    }

    @Test
    public void findOfferPagesInValidDoc() {
        List<String> urls = finder.findProductPagesUris(validDoc);

        assertEquals("/pl/chron-i-wydobywaj-piekno-27-08", urls.get(0));
        assertEquals("/pl/mapy-atlasy-20-08", urls.last());
        assertEquals(12, urls.size());
    }

    @Test
    public void findOfferPagesInEmptyDoc() {
        Document emptyDocument = Jsoup.parse("");
        List<String> urls = finder.findProductPagesUris(emptyDocument);

        assertEquals(0, urls.size());
    }

}
