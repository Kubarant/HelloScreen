package com.hello.screen.model;

import java.util.List;

public class ProfileRegistrationDTO {
	
	private String name;
	private List<CategoryPreferences> preferences;
	private List<String> keywords;
	
	


	public ProfileRegistrationDTO(String name, List<CategoryPreferences> preferences, List<String> keywords) {
		super();
		this.name = name;
		this.preferences = preferences;
		this.keywords = keywords;
	}


	public ProfileRegistrationDTO() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<CategoryPreferences> getPreferences() {
		return preferences;
	}


	public void setPreferences(List<CategoryPreferences> preferences) {
		this.preferences = preferences;
	}


	public List<String> getKeywords() {
		return keywords;
	}


	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}


	@Override
	public String toString() {
		return "ProfileRegistrationDTO [name=" + name + ", preferences=" + preferences + ", keywords=" + keywords + "]";
	}

}
