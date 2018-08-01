package com.hello.screen.datacollectors.biedronka;

import com.hello.screen.ImageWriter;
import com.hello.screen.model.Product;
import com.hello.screen.utils.HtmlCharsRemover;
import com.jaunt.Element;
import com.jaunt.NotFound;

import java.util.Optional;

public class BiedronkaProductExtractor {
    private BiedronkaImageDownloader downloader = new BiedronkaImageDownloader();

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

        return HtmlCharsRemover.removeSpecials(name)
                .replaceAll("[?<>\"\'\\/:*|]", " ");
	}

	private String extractImageUrl(Element elements) throws NotFound {
		return elements.findFirst("<img").getAt("src");
	}

	private String extractPrice(Element elements) throws NotFound {
		String pricePLN = elements.findFirst("<span class=\"pln\"")
				.getChildText();
		String priceCents = elements.findFirst("<span class=\"gr\"")
				.getChildText();
        return pricePLN + '.' + priceCents;
	}

	private String extractAddtitionalInfo(Element elements) {
		StringBuilder builder = new StringBuilder();
		String promotip = getOptionalElementValue("<span class=\"product-promo-tip\"", elements);
		String amount = getOptionalElementValue("<span class=\"amount\"", elements);
		String promoText = getOptionalElementValue("<span class=\"product-promo-text\"", elements);
		return builder.append(promotip).append('&').append(amount).append('&').append(promoText).toString();
	}

    private String getOptionalElementValue(String query, Element elements) {
		try {
			return elements.findFirst(query)
					.getChildText();
		} catch (NotFound e) {
			return "";

		}
	}
}
