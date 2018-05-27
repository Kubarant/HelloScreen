package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.ImageWriter;
import com.hello.screen.model.Product;
import com.jaunt.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.pmw.tinylog.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BiedronkaProductsParser {
	ImageWriter imageWriter = new ImageWriter();
	BiedronkaProductExtractor extractor = new BiedronkaProductExtractor();

	public String getOptionalElementValue(String query, Element elements) {
		try {
			return elements.findFirst(query).getText();
		} catch (NotFound e) {
			return "";

		}
	}

	public List<String> offersUrls() {
		UserAgent userAgent = new UserAgent();
		try {
			userAgent.visit("http://biedronka.pl");
			List<Element> offers = extractOfferUrls(userAgent.doc, "OFERTA");
			List<Element> specialOffers = extractOfferUrls(userAgent.doc, "Akcje Tematyczne");
			List<Element> funOffers = extractOfferUrls(userAgent.doc, "Rozrywka");
			offers.addAll(specialOffers);
			offers.addAll(funOffers);
			List<String> urls = offers.stream().map(el -> el.getAtString("href")).collect(Collectors.toList());
            Logger.debug("Found {} sub pages with offers");
			return urls;
		} catch (Exception e) {
			e.printStackTrace();
			return Arrays.asList();
		}
	}
	

	public List<Element> extractOfferUrls(Document doc, String offerTitle) throws NotFound {
		return doc.findFirst("<a title=\"" + offerTitle + "\"").getParent().findFirst("<ul>").findEvery("<a").toList();

	}


	public List<Product> recieveProductsPage(BiedronkaProductsCollector biedronkaProductsCollector, String url) {
		UserAgent userAgent = new UserAgent();
		try {
			userAgent.visit(url);
		} catch (ResponseException e) {
			return Arrays.asList();
		}
		Elements elements = userAgent.doc.findEvery("<div id=produkt-\\d{1,7}");

		try {
			elements = elements.size() > 0 ? elements
					: userAgent.doc.submit("Tak").findEvery("<div id=produkt-\\d{1,7}");
			elements.toList();
		} catch (Exception e) {
		}

		List<Product> products = elements.toList().stream().map(extractor::createProduct).filter(Optional::isPresent)
				.map(Optional::get).collect(Collectors.toList());

		return products;
	}

	public void downloadImage(String name, String url) {
		Request request = new Request.Builder().url("https://crossorigin.me/" + url). 
				addHeader("Origin", "www.biedronka.pl").addHeader("Referer", "http://www.biedronka.pl/").build();

		OkHttpClient httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();

		
		try(Response response = httpClient.newCall(request).execute()) {
			
			InputStream inputStream = response.body().byteStream();
			BufferedImage image = ImageIO.read(inputStream);
			inputStream.close();
			imageWriter.writeImage(image, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
