package com.hello.screen.services;

import com.hello.screen.model.Product;
import com.hello.screen.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ChecksumServiceTest {

    @Autowired
    ProductRepository repository;

    ChecksumService checksumService;

    List<Product> products;
    List<Product> otherProducts;
    private List<Product> reversed;

    @Before
    public void setUp() {
        checksumService = new ChecksumService();
        products = IntStream.range(0, 50)
                .mapToObj(String::valueOf)
                .map(s -> new Product(s, s, s, s))
                .collect(Collectors.toList());
        otherProducts = IntStream.range(0, 50)
                .mapToObj(String::valueOf)
                .map(s -> new Product(s + s, s + s, s + s, s + s))
                .collect(Collectors.toList());
        reversed = IntStream.range(0, products.size())
                .mapToObj(i -> products.get(products.size() - 1 - i))
                .collect(Collectors.toList());

    }

    @Test
    public void areAlreadyInDbPositive() {
        Boolean isAlready = repository.saveAll(products)
                .flatMap(product -> repository.findAll()
                        .collectList())
                .map(product -> checksumService.areAlreadyInDb(product, products))
                .blockFirst();
        assertTrue(isAlready);
        repository.deleteAll()
                .block();
    }

    @Test
    public void areAlreadyInDbPositiveInOtherOrder() {
        Boolean isAlready = repository.saveAll(products)
                .flatMap(product -> repository.findAll()
                        .collectList())
                .map(product -> checksumService.areAlreadyInDb(product, reversed))
                .blockFirst();
        assertTrue(isAlready);

        repository.deleteAll()
                .block();
    }

    @Test
    public void replaceObjectsIfNotAlreadyStored() {
        repository.saveAll(products)
                .blockLast();
        checksumService.replaceObjectsIfNotAlreadyStored(products, repository)
                .block();
        List<Product> current = repository.findAll()
                .collectList()
                .block();
        assertEquals(current, products);
        repository.deleteAll()
                .block();

    }


    @Test
    public void replaceObjectsIfNotAlreadyStoredNeg() {
        StepVerifier.create(repository.saveAll(products)
                .collectList())
                .expectNext(products)
                .expectComplete();

        StepVerifier.create(checksumService.replaceObjectsIfNotAlreadyStored(otherProducts, repository))
                .expectNext(otherProducts)
                .expectComplete();

        StepVerifier.create(repository.findAll()
                .collectList())
                .expectNext(otherProducts)
                .expectComplete();

        repository.deleteAll()
                .block();
    }


}