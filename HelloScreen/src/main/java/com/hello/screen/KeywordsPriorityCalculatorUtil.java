package com.hello.screen;

import com.hello.screen.model.Product;

import java.util.Arrays;
import java.util.List;

public class KeywordsPriorityCalculatorUtil {
	/**
	 * The "value" of product is calculated by founded keywords in product position
	 * means that the higher position in list keyword is founded that it has bigger value
	 * for example for product containing "Chips", and keywords ["Cola","Chips","Fries"]
	 * the product gain lower value than if it has "Cola" founded and bigger than "fries"
	 */
	public static int productContainance(List<String> keywords, Product prod) {
		int result=1;
		for (int i = 0; i < keywords.size(); i++) {
			boolean contain = isStringsContainKeyword(keywords.get(i),prod.getAdditionalInfo(),prod.getName()); 
			int multiplier =keywords.size()-i;
			result=contain?result+(result*multiplier):result;
		}
		
		return result;
	}

	private static boolean isStringsContainKeyword(String keyword, String... strings) {
		boolean anyContains = Arrays.stream(strings).map(string -> string.contains(keyword)).reduce((a, b) -> a | b)
				.orElse(false);
		return anyContains;
	}

	private static boolean isStringContainsKeywords(List<String> keywords, String arg) {
		return keywords.stream().filter(key -> key.contains(arg)).findFirst().isPresent();
	}

	public static boolean isStringsContainsKeywords(List<String> keywords, String... args) {
		Boolean anyContain = Arrays.stream(args).map(arg -> isStringContainsKeywords(keywords, arg))
				.reduce((a, b) -> a | b).get();

		return anyContain;
	}

}
