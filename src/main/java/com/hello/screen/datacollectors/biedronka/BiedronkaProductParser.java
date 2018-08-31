package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.ImageWriter;
import com.hello.screen.model.Product;
import com.hello.screen.utils.DocumentDownloadUtil;
import io.vavr.collection.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BiedronkaProductParser {
    private BiedronkaImageDownloader downloader = new BiedronkaImageDownloader();

    public List<Product> receiveProductsPage(String url) {
        Elements productElements = DocumentDownloadUtil.downloadDocument(url)
                .select(".productsimple-default");

        return List.ofAll(productElements)
                .map(this::createProduct);
    }

    public Product createProduct(Element elements) {
        String price = extractPrice(elements);
        String name = extractName(elements);
        String imgPath = extractImageUrl(elements);
        String additionalInfo = extractAdditionalInfo(elements);
        imgPath = ImageWriter.makeValidFileName(name);
        downloader.writeImgIfNotAlreadyExist(name, imgPath);

        return new Product(name, additionalInfo, price, imgPath);
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
