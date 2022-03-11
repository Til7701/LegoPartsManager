package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.log.Log;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.*;

public class LegoTableBuilder {

    private final String errorImage = Objects.requireNonNull(getClass().getResource("error-image.png")).toExternalForm();

    public LegoTableBuilder() {

    }

    public void prepareElementsInSets(List<LegoSet> list, OwnLegoSet ownSet) {

        List<LegoTableItem> items = new ArrayList<>();
        List<String> setNameList = new ArrayList<>();

        Set<String> designIDsToShow = new HashSet<>();
        for (LegoSet set : list) {
            setNameList.add(set.getName());
            for (String e : set.getElementIDs()) {
                LegoElement legoElement = LegoDatabase.getLegoElementManager().getElement(e);
                if (legoElement != null) {
                    designIDsToShow.add(legoElement.getDesignID());
                }
            }
        }

        designIDsToShow.addAll(ownSet.getDesignMap().keySet());

        designIDsToShow.stream().parallel().forEach(designID -> prepareDesignID(designID, list, ownSet, items));

        EventBus.post(new ShowInTableEvent(items, setNameList));
    }

    private void prepareDesignID(String designID, List<LegoSet> list, OwnLegoSet ownSet, List<LegoTableItem> items) {
        LegoTableItem item = new LegoTableItem();

        URL path = getClass().getResource("parts/" + designID + ".png");
        if (path != null) {
            item.setImage(new Image(path.toExternalForm()));
        } else {
            Log.waring("Image not found: parts/" + designID + ".png");
            item.setImage(new Image(errorImage));
        }

        item.setDesignID(designID);

        LegoDatabase.getLegoDesignManager().getDesign(designID).ifPresent(design ->
                item.setDescription(design.getDescription())
        );

        item.setOwn(ownSet.getQuantity(designID));

        int fullAmount = 0;
        for (LegoSet set : list) {
            List<LegoTableSetItem> legoTableSetItemList = new ArrayList<>();
            for (String elementID : set.getElementIDs()) {
                LegoElement element = LegoDatabase.getLegoElementManager().getElement(elementID);
                if (element != null && element.getDesignID().equals(designID)) {
                    legoTableSetItemList.add(new LegoTableSetItem(elementID,
                            LegoDatabase.getLegoElementManager().getElement(elementID).getColorID(),
                            set.getQuantity(elementID).orElse(0))
                    );
                }
            }
            for (LegoTableSetItem legoTableSetItem : legoTableSetItemList) {
                fullAmount += legoTableSetItem.getQuantity();
            }
            item.setElements(legoTableSetItemList, set.getName());
        }
        item.setDiff(ownSet.getQuantity(designID) - fullAmount);

        items.add(item);
    }
}
