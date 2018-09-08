package com.hello.screen.services;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@Service
public class ProductsImagesReader {
    @Value("${biedronka.images.path}")
    String imagesPath;

    public byte[] read(String imageName) throws IOException {
        URI uri = URI.create(imagesPath + File.separator + imageName);
        Path imgPath = Paths.get(uri);
        return Files.readAllBytes(imgPath);
    }

    public ResponseEntity readImageEntity(String imageName) {
        Try<ResponseEntity<byte[]>> tryReadImage =
                Try.of(() -> read(imageName))
                        .map(imageBytes -> prepareImageResponseEntity(imageBytes));
        return tryReadImage.getOrElse(ResponseEntity.badRequest()
                .build());
    }

    private ResponseEntity<byte[]> prepareImageResponseEntity(byte[] imageBytes) {
        return ResponseEntity.ok()
                .contentType(IMAGE_JPEG)
                .body(imageBytes);
    }
}
