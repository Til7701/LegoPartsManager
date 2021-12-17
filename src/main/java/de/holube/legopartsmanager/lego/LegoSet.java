package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.io.LegoFileReader;
import de.holube.legopartsmanager.log.Log;

import java.util.*;

/**
 * Maps from Element IDs to Quantity
 */
public class LegoSet {

    private final Map<String, Integer> elementMap = new HashMap<>();
    private String name;

    public LegoSet(String name, String filename) {
        this.name = name;
        readSet(filename);
    }

    public Set<String> getElementIDs() {
        return elementMap.keySet();
    }

    public Optional<Integer> getQuantity(String elementID) {
        if (elementMap.containsKey(elementID)) {
            return Optional.of(elementMap.get(elementID));
        } else return Optional.empty();
    }

    private void readSet(String filename) {
        List<String> fileLines = LegoFileReader.readFile(filename);

        for (int i = 1; i < fileLines.size(); i++) {
            String[] lineArray = fileLines.get(i).split(",");
            if (lineArray.length == 4) {
                String elementID = LegoDatabase.getLegoElementManager().getElementID(new LegoElement(LegoDesign.getSimpleDesignID(lineArray[0]), Integer.parseInt(lineArray[1])));
                int quantity;
                if (elementMap.containsKey(elementID)) {
                    quantity = elementMap.get(elementID) + Integer.parseInt(lineArray[2]);
                } else {
                    quantity = Integer.parseInt(lineArray[2]);
                }
                elementMap.put(elementID, quantity);
            } else {
                Log.waring("Not properly defined Part in " + filename + " line: " + i + ": " + fileLines.get(i));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
