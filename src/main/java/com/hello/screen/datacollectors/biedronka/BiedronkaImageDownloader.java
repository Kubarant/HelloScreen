package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.ImageWriter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BiedronkaImageDownloader {
    public static final String crosme_proxy = "https://crossorigin.me/";
    private static final String heroku_proxy = "https://cors-anywhere.herokuapp.com/";
    private ImageWriter imageWriter;
    private OkHttpClient httpClient;

    BiedronkaImageDownloader() {
        imageWriter = new ImageWriter();
        httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

    }


    void downloadAndSaveImage(String name, String url) {
        Request request = prepareImageRequest(url);
        downloadImage(request).ifPresent(img -> imageWriter.writeImage(img, name));
    }

    private Optional<BufferedImage> downloadImage(Request request) {

        try (Response response = httpClient.newCall(request)
                .execute()) {
            InputStream inputStream = response.body()
                    .byteStream();
            return imageWriter.instreamToImage(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Request prepareImageRequest(String url) {
        return new Request.Builder().url(heroku_proxy + url)
                .addHeader("Origin", "www.biedronka.pl")
                .addHeader("Referer", "http://www.biedronka.pl/")
                .build();
    }

    boolean imgAlreadyExists(String name) {
        return imageWriter.imageAlreadyExists(name);
    }

}
