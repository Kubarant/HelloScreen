package com.hello.screen.utils;

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
    public static int productKeywordsContentRank(List<String> keywords, Product prod) {
        int keywordsContent = 1;
        for (int i = 0; i < keywords.size(); i++) {
            boolean contain = isStringsContainKeyword(keywords.get(i), prod.getAdditionalInfo(), prod.getName());
            int multiplier = keywords.size() - i;
            keywordsContent = contain ? keywordsContent + (keywordsContent * multiplier) : keywordsContent;
        }

        return keywordsContent;
    }

    private static boolean isStringsContainKeyword(String keyword, String... strings) {
        boolean anyContains = Arrays.stream(strings)
                .map(string -> string.contains(keyword))
                .reduce((a, b) -> a | b)
                .orElse(false);
        return anyContains;
    }


}
