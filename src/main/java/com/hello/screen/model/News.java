package com.hello.screen.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public News category(String category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(title, news.title) &&
                Objects.equals(category, news.category) &&
                Objects.equals(date, news.date) &&
                Objects.equals(description, news.description) &&
                Objects.equals(sourceLink, news.sourceLink) &&
                Objects.equals(imgLink, news.imgLink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, category, date, description, sourceLink, imgLink);
    }

    @Override
    public String toString() {
        return "News [id=" + id + ", title=" + title + ", category=" + category + ", date=" + date + ", description="
                + description + ", sourceLink=" + sourceLink + ", imgLink=" + imgLink + "]";
    }


}
