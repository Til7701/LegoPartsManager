package de.holube.legopartsmanager;

import de.holube.legopartsmanager.lego.LegoColorManager;
import de.holube.legopartsmanager.log.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LegoImageConverter {

    public static void main(String[] args) {
        Log.debug("Image Converter Start");
        LegoColorManager legoColorManager = new LegoColorManager("colors.csv");
        Set<Integer> allowedColorIDs = new HashSet<>();
        allowedColorIDs.add(0); //Black
        allowedColorIDs.add(15); //White
        allowedColorIDs.add(7); //Light Gray
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
        allowedColorIDs.add(5); //Dark Pink
        Log.debug("Loading Image");
        BufferedImage image = loadImage("test.jpg");

        Log.debug("Replace Pixels");
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorID = legoColorManager.getClosestColor(allowedColorIDs, image.getRGB(j, i));
                image.setRGB(j, i, legoColorManager.getColor(colorID).getIntColor());
            }
        }

        Log.debug("Save Image");
        saveImage(image, "testExport.png");
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
