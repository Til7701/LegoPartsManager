package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Maps from Design IDs to Designs (Description, Category, Material)
 */
public class LegoDesignManager {

    private final Map<String, LegoDesign> designMap = new HashMap<>();

    public LegoDesignManager(String filename) {
        readDesigns(filename);
    }

    public Optional<LegoDesign> getDesign(String partID) {
        if (designMap.get(partID) == null) {
            return Optional.empty();
        }
        return Optional.of(designMap.get(partID));
    }

    private void readDesigns(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray;
            if (fileLines.get(i).contains("\"")) {
                try {
                    String[] tmp = fileLines.get(i).split("\"");
                    lineArray = new String[4];
                    lineArray[0] = tmp[0].substring(0, tmp[0].length() - 1);
                    lineArray[1] = tmp[1];
                    String[] tmp2 = tmp[tmp.length - 1].split(",");
                    lineArray[2] = tmp2[1];
                    lineArray[3] = tmp2[2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    lineArray = new String[0];
                }
            } else {
                lineArray = fileLines.get(i).split(",");
            }
            if (lineArray.length == 4) {
                designMap.put(lineArray[0], new LegoDesign(lineArray[1], Integer.parseInt(lineArray[2]), lineArray[3]));
            } else {
                Log.waring("Not properly defined Design in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
