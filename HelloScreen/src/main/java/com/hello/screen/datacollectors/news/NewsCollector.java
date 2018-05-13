package com.hello.screen.datacollectors.news;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hello.screen.datacollectors.DataCollector;
import com.hello.screen.model.News;
import com.hello.screen.repository.ProfileRepository;
import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

@Service
public class NewsCollector implements DataCollector<News>{
	@Autowired
	ProfileRepository repository;

	@Scheduled(fixedDelay=60*1000*60, initialDelay=2000)
	public List<News> collect() {
		System.out.println("Starting collecting news");
		Map<String, List<News>> categories = collectCategories();
		repository.findAll().map(profile->{profile.setNews(profile.filterPrefferedNews(categories));return profile;}).subscribe(prof->repository.save(prof).subscribe());
		System.out.println("Ending collecting news");

		return null;
	}
	

	public List<News> extractNewses(String url, String category)  {
		UserAgent agent = new UserAgent();
		try {
			agent.visit(url);
		} catch (ResponseException e) {return Arrays.asList();}
		List<Element> elements = agent.doc.findEvery("<item>").toList();
		List<News> news = elements.stream().map(el -> extractNews(el, category)).filter(Optional::isPresent)
				.map(Optional::get).collect(Collectors.toList());

		return news;
	}

	public Optional<News> extractNews(Element el, String category) {
		try {
			String title = el.findFirst("<title>").getText();
			String date = el.findFirst("<pubDate>").getText();
			String link = el.getText();
			LocalDateTime pubDate = LocalDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);

			Document description = badEncodedHtmlfixer(el.findFirst("<description>").innerHTML());
			String imagesrc = description.findFirst("<img").getAt("src");
			return Optional.ofNullable(new News(title, category, pubDate, "", link, imagesrc));
		} catch (Exception e) {
			return Optional.ofNullable(null);
		}
	}

	public Document badEncodedHtmlfixer(String html) throws ResponseException {
		String fixed = html.replace("&lt;", "<").replace("&amp;", "&").replace("&quot;", "\"").replace("&gt;", ">")
				.replace("&apos;", "'");

		return new UserAgent().openContent(fixed);
	}
	

	public Map<String, List<News>> collectCategories() {
		Map<String,List<News>> categoryNewsMap = new HashMap<>();
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
