package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.model.Product;
import com.hello.screen.utils.DocumentDownloadUtil;
import com.hello.screen.utils.ImageWriter;
import io.vavr.collection.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BiedronkaProductParser {
    private BiedronkaImageDownloader downloader;
    private boolean downloadFlag;

    @Autowired
    public BiedronkaProductParser(BiedronkaImageDownloader downloader, @Value("true") boolean downloadFlag) {
        this.downloader = downloader;
        this.downloadFlag = downloadFlag;
    }

    public List<Product> receiveProductsPage(String url) {
        Document document = DocumentDownloadUtil.downloadDocument(url);
        return receiveProductsPage(document);
    }

    public List<Product> receiveProductsPage(Document document) {
        Elements productElements = document.select(".productsimple-default");
        return List.ofAll(productElements)
                .map(this::createProduct);
    }

    public Product createProduct(Element elements) {
        String price = extractPrice(elements);
        String name = extractName(elements);
        String imageUrl = extractImageUrl(elements);
        String additionalInfo = extractAdditionalInfo(elements);
        String validImageName = ImageWriter.makeValidFileName(name);
        if (downloadFlag)
            downloader.writeImgIfNotAlreadyExist(validImageName, imageUrl);

        return new Product(name, additionalInfo, price, validImageName);
    }

    private String extractAdditionalInfo(Element element) {
        String tio = element.select(".product-promo-tip")
                .text();
        String amount = element.select(".amount")
                .text();
        String promo = element.select(".product-promo-text")
                .text();
        return tio + "&" + amount + "&" + promo;
    }

    private String extractImageUrl(Element element) {
        return element.select("img")
                .attr("src");
    }

    private String extractName(Element element) {
        return element.select(".tile-name")
                .text();
    }

    private String extractPrice(Element element) {
        String price = element.select(".pln")
                .text();
        String cents = element.select(".gr")
                .text();
        return price + "." + cents;
    }
}
