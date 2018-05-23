package com.hello.screen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hello.screen.services.NewsChooserService;

@Document
public class Profile {
	@Id
	private String id;
	private String name;
	private List<String> keywords;
	private List<Product> products;
	private List<String> prefferedCategoriess;
	private List<News> news;
	
	public List<CategoryPreferences> prefferedCategories;


	@Transient
	 NewsChooserService chooserService;

	
	public Profile(String name, List<String> keywords, List<Product> products, List<News> news,
			List<CategoryPreferences> prefferedCategories) {
		super();
		this.name = name;
		this.keywords = keywords;
		this.products = products;
		this.news = news;
		this.prefferedCategories = prefferedCategories;
		products = new ArrayList<>();
		prefferedCategories = new ArrayList<>();
		chooserService = new NewsChooserService();
	}

	public Profile(String name, List<String> keywords) {
		super();
		this.name = name;
		this.keywords = keywords;
		products = new ArrayList<>();
		prefferedCategories = new ArrayList<>();
		chooserService = new NewsChooserService();
	}

	public Profile() {
		super();
		products = new ArrayList<>();
		prefferedCategories = new ArrayList<>();
		chooserService = new NewsChooserService();
	}
	public static Profile registrationDtoToProfile(ProfileRegistrationDTO registrationDTO) {
		Profile profile = new Profile();
		profile.setKeywords(registrationDTO.getKeywords());
		profile.setName(registrationDTO.getName());
		profile.setPrefferedCategories(registrationDTO.getPreferences());
		return profile;
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
		if (result.size() < 30)
			result.addAll(getRandomProducts(products, 30 - result.size()));

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

	public List<News> filterPrefferedNewsss(Map<String, List<News>> news) {
		List<News> interesting = prefferedCategories.stream().map(pcat -> news.get(pcat)).filter(el -> el != null)
				.map(list -> list.subList(0, Math.min(list.size(), 7))).reduce((l1, l2) -> {
					l1.addAll(l2);
					return l1;
				}).get();

		Collections.shuffle(interesting);

		return interesting;
	}

	public List<News> filterPrefferedNews(Map<String, List<News>> news) {
		List<News> choosePrefferedNews = chooserService.choosePrefferedNews(news, prefferedCategories);
		 return choosePrefferedNews;
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


	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public List<CategoryPreferences> getPrefferedCategories() {
		return prefferedCategories;
	}
	
	public void setPrefferedCategories(List<CategoryPreferences> prefferedCategories) {
		this.prefferedCategories = prefferedCategories;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", name=" + name + ", keywords=" + keywords + ", products=" + products + ", news="
				+ news + ", prefferedCategories=" + prefferedCategories + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((news == null) ? 0 : news.hashCode());
		result = prime * result + ((prefferedCategories == null) ? 0 : prefferedCategories.hashCode());
		result = prime * result + ((prefferedCategoriess == null) ? 0 : prefferedCategoriess.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		if (prefferedCategories == null) {
			if (other.prefferedCategories != null)
				return false;
		} else if (!prefferedCategories.equals(other.prefferedCategories))
			return false;
		if (prefferedCategoriess == null) {
			if (other.prefferedCategoriess != null)
				return false;
		} else if (!prefferedCategoriess.equals(other.prefferedCategoriess))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}
	
	
}
