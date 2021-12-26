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

    public int getClosestColor(List<Integer> colorIDs, int r, int g, int b) {
        int minDifference = Integer.MAX_VALUE;
        int closestColor = 0;

        for (int colorID : colorIDs) {
            int difference = Math.abs(getColor(colorID).getR() - r);
            difference += Math.abs(getColor(colorID).getG() - g);
            difference += Math.abs(getColor(colorID).getB() - b);
            if (difference < minDifference) {
                minDifference = difference;
                closestColor = colorID;
            }
        }

        return closestColor;
    }

    private void readColors(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length == 4) {
                int r = Integer.valueOf(lineArray[2].substring( 1, 3 ), 16);
                int g = Integer.valueOf(lineArray[2].substring( 3, 5 ), 16);
                int b = Integer.valueOf(lineArray[2].substring( 5, 7 ), 16);
                colorMap.put(Integer.parseInt(lineArray[0]), new LegoColor(lineArray[1], lineArray[2], r, g, b, lineArray[3].equals("t")));
            } else {
                Log.waring("Not properly defined Color in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
