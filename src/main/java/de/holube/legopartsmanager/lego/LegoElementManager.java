package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps from Element IDs to Design IDs and Colors
 */
public class LegoElementManager {

    private final Map<String, LegoElement> elementMap = new HashMap<>();

    public LegoElementManager(String filename) {
        readElements(filename);
    }

    public LegoElement getElement(String elementID) {
        return elementMap.get(elementID);
    }

    public String getElementID(LegoElement legoElement) {
        return elementMap.keySet().stream().filter(e -> elementMap.get(e).equals(legoElement)).findFirst().orElse("");
    }

    private void readElements(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length >= 3) {
                if (lineArray.length > 3) {
                    for (int j = 3; j < lineArray.length - 1; j++) {
                        lineArray[2] = lineArray[j - 1].concat(lineArray[j]);
                    }
                }
                elementMap.put(lineArray[0], new LegoElement(lineArray[1], Integer.parseInt(lineArray[2])));
            } else {
                Log.waring("Not properly defined Element in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
