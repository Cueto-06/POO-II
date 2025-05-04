package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Color;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.*;

public class preocesadorimagenes {

    static final ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
    static final ExecutorService filterExecutor = Executors.newFixedThreadPool(5);
    static final String OUTPUT_DIR = "imagenes_filtradas";

    public static void main(String[] args) throws IOException, InterruptedException {
        Files.createDirectories(Paths.get(OUTPUT_DIR));
        List<String> urls = Files.readAllLines(Paths.get("urls.txt")).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty() && line.startsWith("http"))
                .toList();

        for (String url : urls) {
            downloadExecutor.submit(() -> downloadAndProcess(url));
        }

        downloadExecutor.shutdown();
        downloadExecutor.awaitTermination(1, TimeUnit.HOURS);
        filterExecutor.shutdown();
        filterExecutor.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Procesamiento finalizado.");
    }

    private static void downloadAndProcess(String urlStr) {
        try {
            URL url = new URL(urlStr);
            BufferedImage image = ImageIO.read(url);
            if (image == null) {
                System.err.println("No se pudo leer imagen: " + urlStr);
                return;
            }

            for (String filter : List.of("sepia", "sharpen", "bw")) {
                filterExecutor.submit(() -> {
                    BufferedImage processed;
                    switch (filter) {
                        case "sepia" -> processed = applySepia(image);
                        case "sharpen" -> processed = applySharpen(image);
                        case "bw" -> processed = applyBlackAndWhite(image);
                        default -> throw new IllegalArgumentException("Filtro inválido");
                    }
                    String fileName = generateUniqueName(urlStr, filter) + ".jpg";
                    saveImage(processed, fileName);
                });
            }

        } catch (IOException e) {
            System.err.println("Error descargando: " + urlStr + " - " + e.getMessage());
        }
    }
//investigando agregamos un hash para que no se repitan nombres ya que se repetian y estos hacia que
// no se guardaran todas las imagenes por lo implementamos esto y creo que funciono
    private static String generateUniqueName(String url, String filter) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((url + filter).getBytes());
            String hash = new BigInteger(1, md.digest()).toString(16);
            return "img_" + hash + "_" + filter;
        } catch (Exception e) {
            return "img_" + System.nanoTime() + "_" + filter;
        }
    }

    private static void saveImage(BufferedImage image, String fileName) {
        try {
            File output = new File(OUTPUT_DIR, fileName);
            ImageIO.write(image, "jpg", output);
            System.out.println("Guardado: " + output.getName());
        } catch (IOException e) {
            System.err.println("Error guardando " + fileName + ": " + e.getMessage());
        }
    }

    private static BufferedImage applySepia(BufferedImage img) {
        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int tr = (int)(0.393*r + 0.769*g + 0.189*b);
                int tg = (int)(0.349*r + 0.686*g + 0.168*b);
                int tb = (int)(0.272*r + 0.534*g + 0.131*b);
                sepia.setRGB(x, y, new Color(clamp(tr), clamp(tg), clamp(tb)).getRGB());
            }
        }
        return sepia;
    }

    private static BufferedImage applySharpen(BufferedImage img) {
        float[] sharpenMatrix = {
                0.f, -1.f, 0.f,
                -1.f, 5.f, -1.f,
                0.f, -1.f, 0.f
        };
        Kernel kernel = new Kernel(3, 3, sharpenMatrix);
        ConvolveOp op = new ConvolveOp(kernel);
        return op.filter(img, null);
    }

    private static BufferedImage applyBlackAndWhite(BufferedImage img) {
        BufferedImage bw = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int gray = (int)(0.3 * c.getRed() + 0.59 * c.getGreen() + 0.11 * c.getBlue());
                Color gColor = new Color(gray, gray, gray);
                bw.setRGB(x, y, gColor.getRGB());
            }
        }
        return bw;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
