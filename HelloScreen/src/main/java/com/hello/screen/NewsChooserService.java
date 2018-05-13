package com.hello.screen;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hello.screen.model.CategoryPreferences;
import com.hello.screen.model.News;
import com.hello.screen.utils.ListUtil;

public class NewsChooserService {
	
	public List<News> filterPrefferedNewssss(Map<String, List<News>> news, List<CategoryPreferences> prefferedCategoriess) {
		Map<String, Integer> categoryNewsSize = news.entrySet().stream()
				.map(ent -> new AbstractMap.SimpleEntry<String, Integer>(ent.getKey(), ent.getValue().size()))
				.collect(Collectors.toMap(ent -> ent.getKey(), ent -> ent.getValue()));
		
		System.out.println("PN SizeMAP \n "+categoryNewsSize+"  \n");


		Map<String, Integer> categoryPrefferedNewsMap = prefferedCategoriess.stream()
				.map(preference -> newsAmountMap(preference, categoryNewsSize))
				.collect(Collectors.toMap(ent -> ent.getKey(), ent -> ent.getValue()));
		
		System.out.println("PN MAP \n "+categoryPrefferedNewsMap);
		
		List<News> newses = categoryPrefferedNewsMap.entrySet().stream()
				.map(ent -> prefferedNewsEx(ent, news))
				.reduce(ListUtil::combine)
				.get();
		Collections.shuffle(newses);
		return newses;
	}

	private List<News> prefferedNewsEx(Entry<String, Integer> ent, Map<String, List<News>> news) {
		return news.get(ent.getKey()).subList(0, ent.getValue()>0?ent.getValue():0);
	}

	private Entry<String, Integer> newsAmountMap(CategoryPreferences preference,
			Map<String, Integer> categoryNewsSize) {
		Integer newsSize = categoryNewsSize.get(preference.getLabel());
		double newsAmountDouble = (preference.getValue() / 100.0d) * (double) 50;
		
		
		int newsAmount = newsSize > newsAmountDouble ?(int) Math.floor(newsAmountDouble): newsSize;
		return new AbstractMap.SimpleEntry<String, Integer>(preference.getLabel(), newsAmount);
	}

}
