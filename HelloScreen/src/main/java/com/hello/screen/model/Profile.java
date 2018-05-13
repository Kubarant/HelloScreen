package com.hello.screen.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hello.screen.utils.ListUtil;

@Document
public class Profile {
	@Id
	private String id;
	private String name;
	private List<String> keywords;
	private List<Product> products;
	private List<String> prefferedCategories;
	private List<News> news;
	public List<CategoryPreferences> prefferedCategoriess;

	public Profile(String name, List<String> keywords) {
		super();
		this.name = name;
		this.keywords = keywords;
		products = new ArrayList<>();
		prefferedCategories = new ArrayList<>();
	}

	public Profile() {
		super();
		products = new ArrayList<>();
		prefferedCategories = new ArrayList<>();
	}

	public ArrayList<Product> filterPrefferedProducts(List<Product> products) {
		ArrayList<Product> result = new ArrayList<>();
		for (Product product : products) {
			boolean containsPath = containKeyword(product.getImagePath().toLowerCase());
			boolean containsTit = containKeyword(product.getName().toLowerCase());
			if (containsPath || containsTit)
				result.add(product);
		}
		System.out.println(result.size());
		if (result.size() < 20)
			result.addAll(getRandomProducts(products, 20 - result.size()));

		return result;
	}

	public boolean containKeyword(String arg) {
		Optional<Boolean> reduce = keywords.stream().map(arg::contains).reduce((a, b) -> a | b);
		return reduce.get();
	}

	private List<Product> getRandomProducts(List<Product> products, int amount) {
		ArrayList<Product> result = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < amount; i++) {
			int rand = random.nextInt(products.size() - 1);
			result.add(products.get(rand));
		}
		return result;
	}

	public List<News> filterPrefferedNews(Map<String, List<News>> news) {
		List<News> interesting = prefferedCategories.stream().map(pcat -> news.get(pcat)).filter(el -> el != null)
				.map(list -> list.subList(0, Math.min(list.size(), 7))).reduce((l1, l2) -> {
					l1.addAll(l2);
					return l1;
				}).get();

		Collections.shuffle(interesting);

		return interesting;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getPrefferedCategories() {
		return prefferedCategories;
	}

	public void setPrefferedCategories(List<String> prefferedCategories) {
		this.prefferedCategories = prefferedCategories;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

}
