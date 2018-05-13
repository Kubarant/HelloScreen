package com.hello.screen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.hello.screen.model.CategoryPreferences;
import com.hello.screen.model.News;
import com.hello.screen.model.Profile;

@RunWith(SpringRunner.class)
public class NewsChooserServiceTest {


	public Map<String, List<News>> createNewsMap() {
		List<News> local = IntStream.range(1, 21).mapToObj(i->new News("local "+i, "Lokalne", null, "", "", "")).collect(Collectors.toList());
		
		List<News> eco = IntStream.range(1, 21).mapToObj(i->new News("eco "+i, "Ekonomia", null, "", "", "")).collect(Collectors.toList());
		
		List<News> pol = IntStream.range(1, 21).mapToObj(i->new News("tech "+i, "Tech", null, "", "", "")).collect(Collectors.toList());
		
		List<News> tech = IntStream.range(1, 21).mapToObj(i->new News("pol "+i, "Polska", null, "", "", "")).collect(Collectors.toList());
		
		List<News> sport = IntStream.range(1, 21).mapToObj(i->new News("sport "+i, "Sport", null, "", "", "")).collect(Collectors.toList());
	
		List<List<News>> news = Arrays.asList(local,eco,pol,tech,sport);
		List<String> keys = news.stream().map(list->list.get(0).getCategory()).collect(Collectors.toList());
		
		HashMap<String, List<News>> newsCategoryMap = new HashMap<>();
		IntStream.range(0, news.size()).forEach(i-> newsCategoryMap.put(keys.get(i), news.get(i)));
		
		return newsCategoryMap;
	}
	@Test
	public void testIfChoosingCorrectly() {
			Profile prof = new Profile();
			prof.prefferedCategoriess = Arrays.asList(new CategoryPreferences("Sport", 0),new CategoryPreferences("Polska", 10),
					new CategoryPreferences("Lokalne", 40),new CategoryPreferences("Ekonomia", 30),new CategoryPreferences("Tech", 20));
			
			Map<String, List<News>> newsMap = createNewsMap();
			List<News> news =null;// prof.filterPrefferedNewssss(newsMap);
			
			Map<Object, Long> news2 =news.stream().map(News::getCategory).collect(Collectors.groupingBy(s->s,Collectors.counting()));
			System.out.println(news2);
			
	}

}
