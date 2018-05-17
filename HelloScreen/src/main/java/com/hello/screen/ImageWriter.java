package com.hello.screen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

import javax.imageio.ImageIO;

public class ImageWriter {
	private final String imagesPath = "C:\\Users\\Kuba\\eclipse\\java-oxygen\\workspace\\git\\HelloScreen\\src\\main\\resources\\public\\imgosy"; // this.getClass().getResource("/productimg").getPath();

	public void writeImage(BufferedImage image, String name) {
		String valid = urlValider(name);
		System.out.println("   " + name);

		try (FileOutputStream outputStream = new FileOutputStream(imagesPath + File.separator + valid + ".jpg");) {

			ImageIO.write(image, "jpg", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearFolder() {
		System.out.println(new File(imagesPath).listFiles().length);
		File[] files = new File(imagesPath).listFiles();
		Arrays.stream(files).forEach(f -> f.delete());
		System.out.println(new File(imagesPath).listFiles().length);
	}

	public boolean imageAlreadyExists(String name) {
		File[] files = new File(imagesPath).listFiles();
		String valid =urlValider(name);
		boolean anyMatch = Arrays.stream(files).anyMatch(file -> file.getName().equals(valid + ".jpg"));

		return anyMatch;
	}

	public Optional<BufferedImage> instreamToImage(InputStream inputStream) {
		try {
			BufferedImage image = ImageIO.read(inputStream);
			BufferedImage rgbImg = prepareImage(image);
			inputStream.close();
			return Optional.ofNullable(rgbImg);
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private BufferedImage prepareImage(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return result;
	}

	public static String urlValider(String name) {
		return name.toLowerCase()
		.replaceAll("ż", "z").replaceAll("ł", "l").replaceAll("ó", "o")
		.replaceAll("ć", "c").replaceAll("ś", "s").replaceAll("ź", "z").replaceAll("ń", "n")
		.replaceAll("ą", "a").replaceAll("ę", "e").replaceAll("[^a-z0-9]", "");
	}

}
