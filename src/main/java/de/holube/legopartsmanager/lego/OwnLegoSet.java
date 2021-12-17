package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OwnLegoSet {

    private final Map<String, Integer> designMap = new HashMap<>();

    public OwnLegoSet(String filename) {
        readSet(filename);
    }

    public Map<String, Integer> getDesignMap() {
        return designMap;
    }

    public int getQuantity(String designID) {
        if (designMap.get(designID) == null) {
            return 0;
        }
        return designMap.get(designID);
    }

    private void readSet(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length >= 2) {
                designMap.put(lineArray[1], Integer.parseInt(lineArray[2]));
            } else {
                Log.waring("Not properly defined Design in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }
}
