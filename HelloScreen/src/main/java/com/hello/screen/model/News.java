package com.hello.screen.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class News {
	@Id
	private String id;
	private String title;
	private String category;
	private LocalDateTime date;
	private String description;
	private String sourceLink;
	private String imgLink;
	
	


	public News(String title, String category, LocalDateTime date, String description, String sourceLink,
			String imgLink) {
		super();
		this.title = title;
		this.category = category;
		this.date = date;
		this.description = description;
		this.sourceLink = sourceLink;
		this.imgLink = imgLink;
	}

	public News() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", category=" + category + ", date=" + date + ", description="
				+ description + ", sourceLink=" + sourceLink + ", imgLink=" + imgLink + "]";
	}
	
	
}
