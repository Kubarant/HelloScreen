package com.hello.screen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Profile {
    public List<CategoryPreferences> preferredCategories;
    @Id
    private String id;
    private String name;
    private List<String> keywords;
    private List<Product> products;
    private List<News> news;


    public Profile(String name, List<String> keywords, List<Product> products, List<News> news,
                   List<CategoryPreferences> preferredCategories) {
        super();
        this.name = name;
        this.keywords = keywords;
        this.products = products;
        this.news = news;
        this.preferredCategories = preferredCategories;
        this.products = new ArrayList<>();
        this.preferredCategories = new ArrayList<>();
    }

    public Profile(String name, List<String> keywords) {
        super();
        this.name = name;
        this.keywords = keywords;
        products = new ArrayList<>();
        preferredCategories = new ArrayList<>();
        news = new ArrayList<>();
    }

    public Profile() {
        super();
        products = new ArrayList<>();
        preferredCategories = new ArrayList<>();
        news = new ArrayList<>();

    }

    public static Profile registrationDtoToProfile(ProfileRegistrationDTO registrationDTO) {
        Profile profile = new Profile();
        profile.setKeywords(registrationDTO.getKeywords());
        profile.setName(registrationDTO.getName());
        profile.setPreferredCategories(registrationDTO.getPreferences());
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

    public Profile setProducts(List<Product> products) {
        this.products = products;
        return this;
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

    public Profile setNews(List<News> news) {
        this.news = news;
        return this;
    }

    public List<CategoryPreferences> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<CategoryPreferences> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    @Override
    public String toString() {
        return "Profile [id=" + id + ", name=" + name + ", keywords=" + keywords + ", products=" + products + ", news="
                + news + ", preferredCategories=" + preferredCategories + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((news == null) ? 0 : news.hashCode());
        result = prime * result + ((preferredCategories == null) ? 0 : preferredCategories.hashCode());
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
        if (preferredCategories == null) {
            if (other.preferredCategories != null)
                return false;
        } else if (!preferredCategories.equals(other.preferredCategories))
            return false;
        if (products == null) {
            return other.products == null;
        } else return products.equals(other.products);
    }


}
