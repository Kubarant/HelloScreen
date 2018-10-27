package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProductRepository;
import com.hello.screen.repository.ProfileRepository;
import com.hello.screen.services.ChecksumService;
import com.hello.screen.services.ProductChoosingService;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BiedronkaPreferredProductsSaver {
    private ProfileRepository repository;
    private ProductRepository productRepository;
    private ProductChoosingService productChooser;
    private ChecksumService checksumService;

    @Autowired
    public BiedronkaPreferredProductsSaver(ProfileRepository repository, ProductRepository productRepository, ProductChoosingService productChooser, ChecksumService checksumService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.productChooser = productChooser;
        this.checksumService = checksumService;
    }

    private Mono<java.util.List<Profile>> saveProfilePreferredProducts(List<Product> products) {
        return repository.findAll()
                .map(profile -> profile.setProducts(productChooser.filterPrefferedProducts(products.asJava(), profile.getKeywords())))
                .flatMap(prof -> repository.save(prof))
                .collectList();
    }

    public void fitProductsToProfile(List<Product> products) {
        checksumService.replaceObjectsIfNotAlreadyStored(products.asJava(), productRepository)
                .flatMap(avoid -> saveProfilePreferredProducts(products))
                .subscribe();
    }


}
