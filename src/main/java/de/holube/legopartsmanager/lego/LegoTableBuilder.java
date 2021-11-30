package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.io.LegoImageReader;
import de.holube.legopartsmanager.log.Log;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class LegoTableBuilder {


    public LegoTableBuilder() {

    }

    public void prepareElementsInSets(List<LegoSet> list, String text) {

        List<LegoTableItem> items = new ArrayList<>();

        Set<String> elementIDsToShow = new HashSet<>();
        for (LegoSet set : list) {
            elementIDsToShow.addAll(set.getElementIDs());
        }

        for (String elementID : elementIDsToShow) {
            LegoTableItem item = new LegoTableItem();
            item.setElementID(elementID);
            LegoElement element = LegoDatabase.getLegoElementManager().getElement(elementID);
            if (element != null) {
                if (element.getDesignID() != null) {
                    item.setDesignID(element.getDesignID());

                    URL path = getClass().getResource("parts_0/" + element.getDesignID() + ".png");
                    if (path != null) {
                        item.setImage(new Image(path.toExternalForm()));
                    } else {
                        Log.waring("Image not found: parts_0/" + element.getDesignID() + ".png");
                    }
                }
                else {
                    item.setDesignID("");
                }

                item.setColorID(element.getColorID());

                LegoDesign design = LegoDatabase.getLegoDesignManager().getDesign(element.getDesignID());
                if (design != null && design.getDescription() != null) item.setDescription(design.getDescription());
                else item.setDesignID("");
            }

            item.setTitanic(list.get(0).getQuantity(elementID).orElse(0));
            item.setOwn(list.get(1).getQuantity(elementID).orElse(0));

            items.add(item);
        }

        if (text != null && !text.equals("")) {
            items = items.stream()
                    .filter(item -> {
                        try {
                            return item.getDescription() != null && item.getElementID() != null && item.getDesignID() != null;
                        } catch (NullPointerException e) {
                            return false;
                        }
                    })
                    .filter(item -> item.getDescription().contains(text) || item.getElementID().contains(text) || item.getDesignID().contains(text))
                    .collect(Collectors.toList());
        }
        EventBus.post(new ShowInTableEvent(items));
    }
}
