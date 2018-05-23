package com.hello.screen.datacollectors.biedronka;

import java.util.Optional;

import com.hello.screen.ImageWriter;
import com.hello.screen.model.Product;
import com.hello.screen.utils.HtmlCharsRemover;
import com.jaunt.Element;
import com.jaunt.NotFound;

public class BiedronkaProductExtractor {
	BiedronkaImageDownloader downloader= new BiedronkaImageDownloader();

	public Optional<Product> createProduct(Element elements) {
		try {
			String price = extractPrice(elements);
			String name = extractName(elements);
			String imgPath = extractImageUrl(elements);
			 if(!downloader.imgAlreadyExists(name))
				 downloader.downloadAndSaveImage(name, imgPath);
			imgPath = ImageWriter.urlValider(name);
			String additionalInfo = extractAddtitionalInfo(elements);

			return Optional.of(new Product(name, additionalInfo, price, imgPath));

		} catch (NotFound exception) {
			return Optional.empty();
		}

	}

	private String extractName(Element elements) {
		String name = getOptionalElementValue("<h4 class=\"tile-name\"", elements);
		String cleanName = HtmlCharsRemover.removeSpecials(name).replaceAll("[?<>\"\'\\/:*|]", " ");
		return cleanName;
	}

	private String extractImageUrl(Element elements) throws NotFound {
		return elements.findFirst("<img").getAt("src");
	}

	private String extractPrice(Element elements) throws NotFound {
		String pricePLN = elements.findFirst("<span class=\"pln\"").getText();
		String priceCents = elements.findFirst("<span class=\"gr\"").getText();
		return new StringBuilder(pricePLN).append('.').append(priceCents).toString();
	}

	private String extractAddtitionalInfo(Element elements) {
		StringBuilder builder = new StringBuilder();
		String promotip = getOptionalElementValue("<span class=\"product-promo-tip\"", elements);
		String amount = getOptionalElementValue("<span class=\"amount\"", elements);
		String promoText = getOptionalElementValue("<span class=\"product-promo-text\"", elements);
		return builder.append(promotip).append('&').append(amount).append('&').append(promoText).toString();
	}

	public String getOptionalElementValue(String query, Element elements) {
		try {
			return elements.findFirst(query).getText();
		} catch (NotFound e) {
			return "";

		}
	}
}
