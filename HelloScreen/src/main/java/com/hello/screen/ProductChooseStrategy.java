package com.hello.screen;

import java.util.List;

import com.hello.screen.model.Product;

public interface ProductChooseStrategy {
	
	
	/**
	 * 
	 * @param <T>
	 * @param products products from which preffered will be choosen
	 * @return preffered products
	 */   
	  List<Product> chooseProducts(List<Product> products);
	
}
