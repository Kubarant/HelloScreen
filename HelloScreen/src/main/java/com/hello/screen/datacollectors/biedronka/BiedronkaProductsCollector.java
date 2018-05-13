package com.hello.screen.datacollectors.biedronka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.repository.ProfileRepository;

import reactor.core.publisher.Flux;

@Service
public class BiedronkaProductsCollector {

	BiedronkaProductsParser productsParser;
	@Autowired
	ProfileRepository repository;

	public BiedronkaProductsCollector() {
		super();
		productsParser = new BiedronkaProductsParser();
	}

	@Scheduled(initialDelay = 2000, fixedDelay = 1000 * 60 * 60)
	public void collectProducts() {
		List<Product> products = recieveProducts();
		// Profile profile = new
		// Profile("Kub",Arrays.asList("lays","czpis","chips","cola","pepsi","cukier","led","elektro","mróz","dan
		// brown","błyskawiczna","gorący
		// kubek","pizza","donatello","giuseppe","tyson","kurczak"));
		Flux<Profile> all = repository.findAll();

		all.map(profile -> {
			profile.setProducts(profile.filterPrefferedProducts(products));
			return profile;
		}).subscribe(prof->repository.save(prof).subscribe());

	}

	public List<Product> recieveProducts() {
		List<String> urls = productsParser.offersUrls();
		List<Product> products = urls.stream().map(url -> productsParser.recieveProductsPage(this, url))
				.filter(list -> !list.isEmpty()).reduce((list, list2) -> {
					list.addAll(list2);
					return list;
				}).get();
		return products;
	}

}
