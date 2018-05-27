package com.hello.screen;

import com.hello.screen.model.Product;

import java.util.List;

public interface ProductChooseStrategy {
	
	
	/**
     *
     *
	 * @param products products from which preffered will be choosen
	 * @return preffered products
	 */   
	  List<Product> chooseProducts(List<Product> products);
	
}
