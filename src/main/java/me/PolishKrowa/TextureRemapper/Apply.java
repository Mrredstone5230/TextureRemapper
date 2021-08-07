package me.PolishKrowa.TextureRemapper;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Apply {

    //1. Images folder
    //2. Output folder
    //3... Data from Analize.java

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        FilenameFilter fileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".png") || name.endsWith(".jpg");
            }
        };

        String images = scanner.nextLine();
        images = images.replace("\\ ", " ");
        images = images.trim();
        images = images + File.separator;

        File imagesFolder = new File(images);
        if (imagesFolder == null || !imagesFolder.isDirectory())
            System.exit(3);

        List<File> rawImages = new ArrayList<>();
        for (String s : imagesFolder.list(fileFilter)) {
            rawImages.add(new File(images + s));
        }

        File processedImagesFolder = new File(images + "processed");
        if (processedImagesFolder.exists())
            FileUtils.deleteDirectory(processedImagesFolder);
        processedImagesFolder.mkdir();

        for (File rawImage : rawImages) {
            resize(rawImage, processedImagesFolder.getAbsolutePath() + File.separator + rawImage.getName(), 128, 128);
        }

        List<File> processedImages = new ArrayList<>();
        for (String s : processedImagesFolder.list(fileFilter)) {
            processedImages.add(new File(images + "processed" + File.separator + s));
        }


        String path = scanner.nextLine();
        path = path.replace("\\ ", " ");
        path = path.trim();
        path = path + File.separator;

        System.out.println(path);
        File outputFolder = new File(path);
        if (outputFolder.exists())
            FileUtils.deleteDirectory(outputFolder);
        outputFolder.mkdir();

        int amount = scanner.nextInt();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            while (!scanner.hasNextLine()) {}
            String line = scanner.nextLine();
            if (line == null || line.equals(""))
                i--;
            else {
                list.add(line);
            }
        }

        Random random = new Random();
        System.out.println(list.get(1));

        for (String s : list) {
            File newfile = new File(path + s);
            System.out.println(path + s);
            FileUtils.copyFile(processedImages.get(random.nextInt(processedImages.size())), newfile);
        }

        System.out.println("Success !");
    }




    // CODE FROM https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
    // SLIGHTLY MODIFIED BY MYSELF
    public static void resize(File inputFile,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        //set jpg to png
        if (outputImagePath.endsWith(".jpg"))
            outputImagePath = outputImagePath.replace(".jpg", ".png");

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        outputImagePath = outputImagePath.replace("\\ ", " ");
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
}
