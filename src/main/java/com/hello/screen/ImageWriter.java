package com.hello.screen;

import org.pmw.tinylog.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

public class ImageWriter {

    public static String makeValidFileName(String name) {
        return name.toLowerCase()
                .replaceAll("ż", "z")
                .replaceAll("ł", "l")
                .replaceAll("ó", "o")
                .replaceAll("ć", "c")
                .replaceAll("ś", "s")
                .replaceAll("ź", "z")
                .replaceAll("ń", "n")
                .replaceAll("ą", "a")
                .replaceAll("ę", "e")
                .replaceAll("[^a-z0-9]", "");
    }

    public void writeImage(BufferedImage image, String name, String imagesPath) {
        String validName = makeValidFileName(name);
        Logger.debug("Writing {}.jpg image named {}", name, validName);

        try (FileOutputStream outputStream = new FileOutputStream(imagesPath + File.separator + validName + ".jpg")) {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean imageAlreadyExists(String name, String imagesPath) {
        File[] files = new File(imagesPath).listFiles();
        File[] safeFiles = Optional.ofNullable(files)
                .orElse(new File[]{});
        String validName = makeValidFileName(name);

        return Arrays.stream(safeFiles)
                .anyMatch(file -> file.getName()
                        .equals(validName + ".jpg"));
    }

    private BufferedImage prepareImage(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.createGraphics()
                .drawImage(image, 0, 0, Color.WHITE, null);
        return result;
    }

    public Optional<BufferedImage> inStreamToImage(InputStream inputStream) {
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


}
