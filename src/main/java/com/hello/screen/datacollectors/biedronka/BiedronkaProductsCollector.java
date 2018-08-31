package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import com.hello.screen.repository.ProductRepository;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.ChecksumService;
import com.hello.screen.services.ProductChoosingService;
import io.vavr.collection.List;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class BiedronkaProductsCollector {

    private static final String BIEDRONKA_URL = "http://biedronka.pl";
    private BiedronkaProductParser productParser;
    private ProductChoosingService productChooser;
    private ChecksumService checksumService;
    private ProfileRepository repository;
    private BiedronkaProductsPagesFinder productsFinder;
    private ProductRepository productRepository;


    @Autowired
    public BiedronkaProductsCollector(ChecksumService checksumService, ProductChoosingService productChooser, ProfileRepository repository, ProductRepository productRepository) {
        this.checksumService = checksumService;
        this.productChooser = productChooser;
        this.repository = repository;
        this.productRepository = productRepository;
        productsFinder = new BiedronkaProductsPagesFinder();
        productParser = new BiedronkaProductParser();

    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000 * 60 * 60)
    public void collectProducts() {
        Logger.info("Start collecting products");
        List<Product> products = receiveProducts();
        Logger.info("Found {} products", products.size());

        checksumService.replaceObjectsIfNotAlreadyStored(products.asJava(), productRepository)
                .doOnNext(aVoid -> saveProfilePreferredProducts(products))
                .subscribe();
    }

    private void saveProfilePreferredProducts(List<Product> products) {
        repository.findAll()
                .map(profile -> profile.setProducts(productChooser.filterPrefferedProducts(products.asJava(), profile.getKeywords())))
                .subscribe(prof -> repository.save(prof)
                        .subscribe());
    }

    private List<Product> receiveProducts() {
        return productsFinder.findProductPagesUris(BIEDRONKA_URL)
                .map(productParser::receiveProductsPage)
                .reduce((a, b) -> a.appendAll(b));
    }

}
