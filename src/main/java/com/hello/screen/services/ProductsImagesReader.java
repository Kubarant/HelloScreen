package com.hello.screen.services;

import com.hello.screen.datacollectors.biedronka.BiedronkaImageDownloader;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@Service
public class ProductsImagesReader {
    @Value(BiedronkaImageDownloader.IMAGES_DIRECTORY_SPEL)
    private String imagesPath;


    public ResponseEntity readImageEntity(String imageName) {
        Try<ResponseEntity<byte[]>> tryReadImage =
                Try.of(() -> read(imageName))
                        .map(this::prepareImageResponseEntity);
        return tryReadImage.getOrElse(ResponseEntity.badRequest()
                .build());
    }

    private ResponseEntity<byte[]> prepareImageResponseEntity(byte[] imageBytes) {
        return ResponseEntity.ok()
                .contentType(IMAGE_JPEG)
                .body(imageBytes);
    }

    private byte[] read(String imageName) throws IOException {
        Path imgPath = Paths.get(imagesPath + File.separator + imageName);
        return Files.readAllBytes(imgPath);
    }
}
