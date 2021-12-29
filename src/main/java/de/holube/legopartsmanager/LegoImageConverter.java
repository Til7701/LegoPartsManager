package de.holube.legopartsmanager;

import de.holube.legopartsmanager.lego.LegoColorManager;
import de.holube.legopartsmanager.log.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LegoImageConverter {

    public static void main(String[] args) {
        Log.debug("Image Converter Start");
        LegoColorManager legoColorManager = new LegoColorManager("colors.csv");
        Set<Integer> allowedColorIDs = new HashSet<>();
        allowedColorIDs.add(0); //Black
        allowedColorIDs.add(15); //White
        /*allowedColorIDs.add(7); //Light Gray
        allowedColorIDs.add(8); //Dark Gray
        allowedColorIDs.add(71); //Light Bluish Gray
        allowedColorIDs.add(72); //Dark Bluish Gray
        allowedColorIDs.add(1); //Blue
        allowedColorIDs.add(9); //Light Blue
        allowedColorIDs.add(2); //Green
        allowedColorIDs.add(17); //Light Green
        allowedColorIDs.add(10); //Bright Green
        allowedColorIDs.add(27); //Lime
        allowedColorIDs.add(4); //Red
        allowedColorIDs.add(320); //Dark Red
        allowedColorIDs.add(25); //Orange
        allowedColorIDs.add(14); //Yellow
        allowedColorIDs.add(13); //Pink
        allowedColorIDs.add(29); //Bright Pink
        allowedColorIDs.add(5); //Dark Pink*/
        Log.debug("Loading Image");
        BufferedImage image = loadImage("mietz.jpg");
        int left = 120;
        int right = 80;
        int top = 100;
        int bottom = 100;
        image = image.getSubimage(left, top, image.getWidth() - left - right, image.getHeight() - top - bottom);

        Log.debug("Scaling Image");
        image = scale(image, 3, 4);

        Log.debug("Replace and count Pixels");
        Map<Integer, Integer> colorFrequency = new HashMap<>();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorID = legoColorManager.getClosestColor(allowedColorIDs, image.getRGB(j, i));
                image.setRGB(j, i, legoColorManager.getColor(colorID).getIntColor());

                if (colorFrequency.containsKey(colorID)) colorFrequency.put(colorID, colorFrequency.get(colorID) + 1);
                else colorFrequency.put(colorID, 1);
            }
        }

        Log.debug("Save Image");
        saveImage(image, "testExport.png");

        colorFrequency.forEach((colorID, value) -> Log.info(legoColorManager.getColor(colorID).getName() + ": " + value));
    }

    private static BufferedImage scale(BufferedImage image, int width, int height) {
        double ratio = image.getHeight() / (double) image.getWidth();
        width *= 16;
        height *= 16;
        int absoluteHeight = (int) (width * ratio);

        BufferedImage result = new BufferedImage(width, absoluteHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = result.createGraphics();
        g.drawImage(image, 0, 0, width, absoluteHeight, null);
        g.dispose();

        if (result.getHeight() < height) {
            //return result.getSubimage(0, (result.getHeight() % 16) / 2, width, result.getHeight() - (result.getHeight() % 16));
        } else if (result.getHeight() > height) {
            return result.getSubimage(0, 0, width, height);
        }
        return result;
    }

    private static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    private static void saveImage(BufferedImage image, String filename) {
        try {
            File outputfile = new File(filename);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
