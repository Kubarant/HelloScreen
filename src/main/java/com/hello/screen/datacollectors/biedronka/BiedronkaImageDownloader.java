package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.utils.ImageWriter;
import io.vavr.control.Option;
import io.vavr.control.Try;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BiedronkaImageDownloader {
    public static final String IMAGES_DIRECTORY_SPEL = "${biedronka.images.path:#{systemProperties['user.home'].concat('\\helloapp\\')}}";

    @Value("${cors.proxy.url}")
    private String corsProxyUrl;
    private String imagesDirectoryPath;
    private ImageWriter imageWriter;
    private OkHttpClient httpClient;

    public BiedronkaImageDownloader(@Value(value = IMAGES_DIRECTORY_SPEL) String imagesDirectoryPath) {
        this.imagesDirectoryPath = imagesDirectoryPath;
        imageWriter = new ImageWriter();
        httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }


    public void downloadAndSaveImage(String name, String url) {
        Request request = prepareImageDownloadRequest(url);
        downloadImage(request).ifPresent(img -> imageWriter.writeImage(img, name, imagesDirectoryPath));
    }

    private Optional<BufferedImage> downloadImage(Request request) {
        Try<InputStream> makeRequest = Try.of(() -> getRequestInputStreamSafe(request))
                .onFailure(e -> Logger.warn("Can't download an Image " + e));
        ByteArrayInputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        InputStream imgInputStream = makeRequest.getOrElse(emptyInputStream);
        return imageWriter.inStreamToImage(imgInputStream);

    }

    private ResponseBody getRequestInputStream(Request request) throws IOException {
        return httpClient.newCall(request)
                .execute()
                .body();
    }

    private InputStream getRequestInputStreamSafe(Request request) throws IOException {
        ByteArrayInputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        return Option.of(getRequestInputStream(request))
                .map(ResponseBody::byteStream)
                .getOrElse(emptyInputStream);
    }

    private Request prepareImageDownloadRequest(String url) {
        return new Request.Builder().url(corsProxyUrl + url)
                .addHeader("Origin", "www.biedronka.pl")
                .addHeader("Referer", "http://www.biedronka.pl/")
                .build();
    }

    boolean imgAlreadyExists(String name) {
        return imageWriter.imageAlreadyExists(name, imagesDirectoryPath);
    }

    public void writeImgIfNotAlreadyExist(String name, String imgPath) {
        if (!imgAlreadyExists(name))
            downloadAndSaveImage(name, imgPath);
    }
}