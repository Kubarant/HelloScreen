package com.hello.screen.model;

import org.springframework.data.annotation.Id;

public class Product {
	@Id
	private String id;
	private String name;
	private String additionalInfo;
	private String price;
	private String imagePath;
	
	
	public Product(String id, String name, String additionalInfo, String price, String imagePath) {
		super();
		this.id = id;
		this.name = name;
		this.additionalInfo = additionalInfo;
		this.price = price;
		this.imagePath = imagePath;
	}
	public Product(String name, String additionalInfo, String price, String imagePath) {
		super();
		this.name = name;
		this.additionalInfo = additionalInfo;
		this.price = price;
		this.imagePath = imagePath;
	}
	public Product() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", additionalInfo=" + additionalInfo + ", price=" + price
				+ ", imagePath=" + imagePath + "]";
	}
	
	
	
	
	
}