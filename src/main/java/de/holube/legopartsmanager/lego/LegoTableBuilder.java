package de.holube.legopartsmanager.lego;

import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.log.Log;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.*;

public class LegoTableBuilder {

    private String errorImage = Objects.requireNonNull(getClass().getResource("error-image.png")).toExternalForm();

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

        for (String designID : designIDsToShow) {
            LegoTableItem item = new LegoTableItem();

            URL path = getClass().getResource("parts_0/" + designID + ".png");
            if (path != null) {
                item.setImage(new Image(path.toExternalForm()));
            } else {
                Log.waring("Image not found: parts_0/" + designID + ".png");
                item.setImage(new Image(errorImage));
            }

            item.setDesignID(designID);

            if (LegoDatabase.getLegoDesignManager().getDesign(designID).isPresent()) {
                item.setDescription(LegoDatabase.getLegoDesignManager().getDesign(designID).get().getDescription());
            }

            item.setOwn(ownSet.getQuantity(designID));

            List<LegoTableSetItem> legoTableSetItemList = new ArrayList<>();
            for (LegoSet set : list) {
                for (String elementID : set.getElementIDs()) {
                    LegoElement element = LegoDatabase.getLegoElementManager().getElement(elementID);
                    if (element != null && element.getDesignID().equals(designID)) {
                        legoTableSetItemList.add(new LegoTableSetItem(elementID,
                                LegoDatabase.getLegoElementManager().getElement(elementID).getColorID(),
                                set.getQuantity(elementID).orElse(0))
                        );
                    }
                }
                item.setElements(legoTableSetItemList, set.getName());
            }

            items.add(item);
        }

        EventBus.post(new ShowInTableEvent(items, setNameList));
    }
}
