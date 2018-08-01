package com.hello.screen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;

@RunWith(SpringRunner.class)
public class ProductsCollectorTest {

	@Test
	public void pa() throws Exception {
		// Profile profile = new Profile("Kub",
		// Arrays.asList("lays","czips","chips","cola","pepsi","cukierki","hamburg","elektro","mróz","dan
		// brown","błyskawiczna","gorący
		// kubek","pizza","donatello","giuseppe","tyson","kurczak"));
		// NewsCollector collector = new NewsCollector();
		// collector.collect();
		// new ImageWriter().clearFolder();

		String imagesPath = "C:\\Users\\Kuba\\Desktop\\imgosy";
		File file = new File(imagesPath);
		File[] listFiles = file.listFiles();
		Arrays.stream(listFiles).forEach(f -> {
			String nn = f.getName().toLowerCase().replaceAll("jpg", ".jpg"); //replaceAll("[^a-zA-Z0-9]+","");
//					.replaceAll("ż", "z").replaceAll("ł", "l").replaceAll("ó", "o")
//					.replaceAll("ć", "c").replaceAll("ś", "s").replaceAll("ź", "z").replaceAll("ń", "n")
//					.replaceAll("ą", "a").replaceAll("ę", "e").
//			f.renameTo(new File(f.getParent(),nn));
		});
	}

}
