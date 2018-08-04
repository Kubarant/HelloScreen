package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import com.hello.screen.repository.ProductRepository;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.ChecksumService;
import com.hello.screen.services.ProductChoosingService;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BiedronkaProductsCollector {

    @Autowired
    ChecksumService checksumService;
    @Autowired
    private ProductChoosingService productChooser;
    private BiedronkaProductsParser productsParser;

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProductRepository productRepository;


    public BiedronkaProductsCollector() {
        super();
        productsParser = new BiedronkaProductsParser();
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000 * 60 * 60)
    public void collectProducts() {
        List<Product> products = recieveProducts();
        Logger.info("Found {} products", products.size());

        checksumService.replaceObjectsIfNotAlreadyStored(products, productRepository)
                .doOnNext(aVoid -> saveProfilePreferredProducts(products))
                .subscribe();


    }

    private Disposable saveProfilePreferredProducts(List<Product> products) {
        return repository.findAll()
                .map(profile -> profile.setProducts(productChooser.filterPrefferedProducts(products, profile.getKeywords())))
                .subscribe(prof -> repository.save(prof)
                        .subscribe());
    }

    private List<Product> recieveProducts() {
        List<String> urls = productsParser.offersUrls();
        return urls.stream()
                .map(url -> productsParser.recieveProductsPage(this, url))
                .filter(list -> !list.isEmpty())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

    }

}
