package com.hello.screen.controllers;

import com.hello.screen.services.ProductsImagesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;


@Controller
public class ProductsImageController {
    private ProductsImagesReader imagesReader;

    @Autowired
    public ProductsImageController(ProductsImagesReader imagesReader) {
        this.imagesReader = imagesReader;
    }

    @GetMapping("/imgosy/{imageName}")
    public Mono<ResponseEntity> news(@PathVariable String imageName) {
        return Mono.just(imagesReader.readImageEntity(imageName));
    }
}
