package com.hello.screen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Profile {
	@Id
	private String id;
	private String name;
	private List<String> keywords;
	private List<Product> products;
	private List<News> news;
	
	public List<CategoryPreferences> prefferedCategories;



	
	public Profile(String name, List<String> keywords, List<Product> products, List<News> news,
			List<CategoryPreferences> prefferedCategories) {
		super();
		this.name = name;
		this.keywords = keywords;
		this.products = products;
		this.news = news;
		this.prefferedCategories = prefferedCategories;
		this.products = new ArrayList<>();
		this.prefferedCategories = new ArrayList<>();
	}

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
	public static Profile registrationDtoToProfile(ProfileRegistrationDTO registrationDTO) {
		Profile profile = new Profile();
		profile.setKeywords(registrationDTO.getKeywords());
		profile.setName(registrationDTO.getName());
		profile.setPrefferedCategories(registrationDTO.getPreferences());
		return profile;
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

	public List<Product> setProducts(List<Product> products) {
		this.products = products;
		return this.products;
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

	public List<News> setNews(List<News> news) {
		this.news = news;
		return this.news;
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
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}
	
	
}
