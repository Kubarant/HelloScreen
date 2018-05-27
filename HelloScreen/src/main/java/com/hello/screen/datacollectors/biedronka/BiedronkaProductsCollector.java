package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.ProductChoosingService;
import com.hello.screen.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class BiedronkaProductsCollector {

    @Autowired
    private ProductChoosingService productChooser;

    private BiedronkaProductsParser productsParser;

    @Autowired
    private ProfileRepository repository;

    public BiedronkaProductsCollector() {
        super();
        productsParser = new BiedronkaProductsParser();
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000 * 60 * 60)
    public void collectProducts() {
        List<Product> products = recieveProducts();
        Flux<Profile> all = repository.findAll();

        all.map(profile -> {
            profile.setProducts(productChooser.filterPrefferedProducts(products, profile.getKeywords()));
            return profile;
        })
                .subscribe(prof -> repository.save(prof)
                        .subscribe());

    }

    private List<Product> recieveProducts() {
        List<String> urls = productsParser.offersUrls();
        return urls.stream()
                .map(url -> productsParser.recieveProductsPage(this, url))
                .filter(list -> !list.isEmpty())
                .reduce((list, list2) -> ListUtil.combine(list, list))
                .get();

    }

}
