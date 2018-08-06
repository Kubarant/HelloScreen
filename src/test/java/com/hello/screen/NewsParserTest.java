package com.hello.screen;


import com.hello.screen.datacollectors.news.NewsParser;
import com.hello.screen.model.News;
import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class NewsParserTest {

    private static final int NEWS_AMOUNT_IN_VALID_DOCUMENT = 6;
    private Document validNewsDoc;
    private NewsParser newsParser;

    @Before
    public void setUp() throws Exception {
        InputStream newsInput = getClass().getClassLoader()
                .getResourceAsStream("news.xml");
        validNewsDoc = Jsoup.parse(newsInput, "UTF-8", "", Parser.xmlParser());
        newsParser = new NewsParser();
    }


    @Test
    public void parseNewsFromValidDoc() {
        List<News> news = newsParser.parseNews(validNewsDoc, "All");
        News firstNews = new News("Chicago appeals for help after dozens shot over weekend", "All", LocalDateTime.parse("2018-08-06T12:04:04"), "Chicago appeals for help after dozens shot over weekend Full coverage", "https://www.bbc.com/news/world-us-canada-45084264", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-aODJi5t1PZCY0SbgWDGIk4hjEY8MT4RUNKbWtttUlNGzj7fmzAjfPBabjpKs1GV94dVvJyacqfc");
        News lastNews = new News("Tom Brady apologizes to Randy Moss for not throwing him the ball more", "All", LocalDateTime.parse("2018-08-05T19:31:01"), "Tom Brady apologizes to Randy Moss for not throwing him the ball more Randy Moss wears tie at Hall of Fame ceremony that honors black men, women killed in police shootings Full coverage", "https://www.usatoday.com/story/sports/nfl/2018/08/05/tom-brady-randy-moss-new-england-patriots-hall-fame/910655002/", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTJX0CoZjV8YX_-QROy-1CFKT0ijGqE6XWvyvPutTI2ifGGDm_ycEsgaK6OBtxYVeE3DeaCAdGlpA");

        assertEquals(NEWS_AMOUNT_IN_VALID_DOCUMENT, news.size());
        assertEquals(news.get(0), firstNews);
        assertEquals(news.last(), lastNews);
    }

    @Test
    public void parseNewsFromEmptyDoc() {
        Document document = Jsoup.parse("");
        List<News> all = newsParser.parseNews(document, "All");
        assertEquals(0, all.size());
    }


}
