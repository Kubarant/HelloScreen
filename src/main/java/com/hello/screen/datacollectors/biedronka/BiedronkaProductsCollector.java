package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import io.vavr.collection.List;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class BiedronkaProductsCollector {

    private static final String BIEDRONKA_URL = "http://biedronka.pl";
    private BiedronkaProductParser productParser;
    private BiedronkaProductsPagesFinder productsFinder;
    private BiedronkaPreferredProductsSaver preferredProductsSaver;

    @Autowired
    public BiedronkaProductsCollector(BiedronkaProductParser productParser, BiedronkaProductsPagesFinder productsFinder, BiedronkaPreferredProductsSaver preferredProductsSaver) {
        this.productParser = productParser;
        this.productsFinder = productsFinder;
        this.preferredProductsSaver = preferredProductsSaver;
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000 * 60 * 60)
    public void collectProducts() {
        Logger.info("Start collecting products");
        List<Product> products = receiveProducts();
        Logger.info("Found {} products", products.size());
        preferredProductsSaver.fitProductsToProfile(products);
    }

    private List<Product> receiveProducts() {
        return productsFinder.findProductPagesUrls(BIEDRONKA_URL)
                .map(productParser::receiveProductsPage)
                .reduce(List::appendAll);
    }

}
