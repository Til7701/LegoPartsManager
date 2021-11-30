package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps from Design IDs to Designs (Description, Category, Material)
 */
public class LegoDesignManager {

    private final Map<String, LegoDesign> designMap = new HashMap<>();

    public LegoDesignManager(String filename) {
        readDesigns(filename);
    }

    public LegoDesign getDesign(String partID) {
        return designMap.get(partID);
    }

    private void readDesigns(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length == 4) {
                designMap.put(lineArray[0], new LegoDesign(lineArray[1], Integer.parseInt(lineArray[2]), lineArray[3]));
            } else {
                Log.waring("Not properly defined Design in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
