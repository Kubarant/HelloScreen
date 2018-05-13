package com.hello.screen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.hello.screen.model.Product;

public class Strategies {

	public ProductChooseStrategy randomChoosing(Optional<Random> rand) {
		Random random = rand.orElse(new Random());
		return prod -> {
			Collections.shuffle(prod, random);
			return prod;
		};
	}

	public ProductChooseStrategy keywordChoosing(List<String> keywords) {

		Comparator<Product> keywordsContainance = (el, el2) -> {
			int pvalue = KeywordsPriorityCalculatorUtil.productContainance(keywords, el);
			int pvalue2 = KeywordsPriorityCalculatorUtil.productContainance(keywords, el2);
			return pvalue - pvalue2;
		};
		return prod -> {
			prod.sort(keywordsContainance);
			return prod;
		};
	}

}
