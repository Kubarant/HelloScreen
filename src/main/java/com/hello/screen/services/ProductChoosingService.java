package com.hello.screen.services;

import com.hello.screen.model.Product;
import com.hello.screen.utils.ListUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductChoosingService {

    @Value("${products.default.amount}")
    private int defaultProductsAmount;

    /**
     * @return products containing any keyword and if it's less than specified in app.properties products it filled to default amount with random products
     */
    public List<Product> filterPrefferedProducts(List<Product> products, List<String> keywords) {
        ArrayList<Product> result = extractProductsContainingKeywords(products, keywords);
//        Logger.info("prodsize {}  {} {}",products.size(),result.size(),keywords);

        fillWithRandomProductsToFixedSize(products, result, defaultProductsAmount);

        return ListUtil.sublist(result, 0, defaultProductsAmount);
    }

    public List<Product> filterPrefferedProducts(List<Product> products, List<String> keywords, int productsAmount) {
        List<Product> result = filterPrefferedProducts(products, keywords);
        fillWithRandomProductsToFixedSize(products, result, productsAmount);
        return result;
    }


    private ArrayList<Product> extractProductsContainingKeywords(List<Product> products, List<String> keywords) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            boolean isImgPathHasKeyword = containKeyword(product.getImagePath()
                    .toLowerCase(), keywords);
            boolean isNameContainsKeyword = containKeyword(product.getName()
                    .toLowerCase(), keywords);
            if (isImgPathHasKeyword || isNameContainsKeyword)
                result.add(product);
        }
        return result;
    }

    private void fillWithRandomProductsToFixedSize(List<Product> products, List<Product> filled, int size) {
        if (filled.size() < size)
            filled.addAll(getRandomProducts(products, defaultProductsAmount - filled.size()));
    }


    private boolean containKeyword(String arg, List<String> keywords) {
        Optional<Boolean> reduce = keywords.stream()
                .map(arg::contains)
                .reduce((a, b) -> a | b);
        return reduce.get();
    }

    private List<Product> getRandomProducts(List<Product> products, int amount) {
        HashSet<Product> set = new HashSet<>();
        ArrayList<Product> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; set.size() < amount || i < products.size(); i++) {
            int rand = random.nextInt(products.size() - 1);
            set.add(products.get(rand));
        }

        result.addAll(set);
        return result;
    }
}