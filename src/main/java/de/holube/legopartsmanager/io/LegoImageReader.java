package de.holube.legopartsmanager.io;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import de.holube.legopartsmanager.log.Log;
import javafx.scene.image.Image;

public class LegoImageReader {

    private LegoImageReader() {

    }

    public static BufferedImage readBufferedImageFromDesignID(String designID) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(Objects.requireNonNull(LegoImageReader.class.getResource(designID + ".png")));

        return img;
    }

    public static Image readImageFromDesignID(String designID) {
        Image result;
        try {
            URL url = LegoImageReader.class.getResource("parts_0/" + designID + ".png");
            if (url != null) {
                result = new Image(url.getFile());
                return result;
            }
            /*result = new Image("/io/parts_0/" + designID + ".png");
            return result;*/
        } catch (IllegalArgumentException e) {
            Log.waring("Image not found: parts_0/" + designID + ".png");
            e.printStackTrace();
        }
        return null;
    }
}
