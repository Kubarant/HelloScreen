package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.ImageWriter;
import io.vavr.control.Try;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BiedronkaImageDownloader {
    public static final String crosme_proxy = "https://crossorigin.me/";
    private static final String heroku_proxy = "https://cors-anywhere.herokuapp.com/";
    private ImageWriter imageWriter;
    private OkHttpClient httpClient;

    public BiedronkaImageDownloader() {
        imageWriter = new ImageWriter();
        httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public void downloadAndSaveImage(String name, String url) {
        Request request = prepareImageDownloadRequest(url);
        downloadImage(request).ifPresent(img -> imageWriter.writeImage(img, name));
    }

    private Optional<BufferedImage> downloadImage(Request request) {
        Try<InputStream> makeRequest = Try.of(() -> getRequestInputStream(request));
        ByteArrayInputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        InputStream imgInputStream = makeRequest.getOrElse(emptyInputStream);
        return imageWriter.inStreamToImage(imgInputStream);

    }

    private InputStream getRequestInputStream(Request request) throws IOException {
        return httpClient.newCall(request)
                .execute()
                .body()
                .byteStream();
    }

    private Request prepareImageDownloadRequest(String url) {
        return new Request.Builder().url(heroku_proxy + url)
                .addHeader("Origin", "www.biedronka.pl")
                .addHeader("Referer", "http://www.biedronka.pl/")
                .build();
    }

    boolean imgAlreadyExists(String name) {
        return imageWriter.imageAlreadyExists(name);
    }

    public void writeImgIfNotAlreadyExist(String name, String imgPath) {
        if (!imgAlreadyExists(name))
            downloadAndSaveImage(name, imgPath);
    }
}