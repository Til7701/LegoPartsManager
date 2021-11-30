package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps from Color IDs to LegoColors (Name, RGB, Transparent)
 */
public class LegoColorManager {

    private final Map<Integer, LegoColor> colorMap = new HashMap<>();

    public LegoColorManager(String filename) {
        readColors(filename);
    }

    public LegoColor getColor(int colorID) {
        return colorMap.get(colorID);
    }

    private void readColors(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length == 4) {
                colorMap.put(Integer.parseInt(lineArray[0]), new LegoColor(lineArray[1], lineArray[2], lineArray[3].equals("t")));
            } else {
                Log.waring("Not properly defined Color in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
