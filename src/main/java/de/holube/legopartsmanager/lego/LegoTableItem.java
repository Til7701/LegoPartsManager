package de.holube.legopartsmanager.lego;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegoTableItem {

    private final Property<Image> image = new SimpleObjectProperty<>();
    private final StringProperty designID = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty own = new SimpleIntegerProperty();
    private final Map<String, List<LegoTableSetItem>> elementsList = new HashMap<>();
    private final Map<String, StringProperty> elementsListString = new HashMap<>();
    private final IntegerProperty diff = new SimpleIntegerProperty();

    public void setImage(Image image) {
        this.image.setValue(image);
    }

    public ObjectProperty<Image> imageProperty() {
        return new SimpleObjectProperty<>(image.getValue());
    }

    public String getDesignID() {
        return designID.get();
    }

    public StringProperty designIDProperty() {
        return designID;
    }

    public void setDesignID(String designID) {
        this.designID.set(designID);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getOwn() {
        return own.get();
    }

    public IntegerProperty ownProperty() {
        return own;
    }

    public void setOwn(int own) {
        this.own.set(own);
    }

    public StringProperty getElements(String setName) {
        return elementsListString.get(setName);
    }

    public void setElements(List<LegoTableSetItem> elements, String setName) {
        StringBuilder builder = new StringBuilder();
        elementsList.put(setName, elements);
        for (LegoTableSetItem legoElement : elements) {
            builder.append(LegoDatabase.getLegoColorManager().getColor(legoElement.getColorID()).getName())
                    .append(": ")
                    .append(legoElement.getQuantity())
                    .append(" (")
                    .append(legoElement.getElementID())
                    .append(")\n");
        }

        this.elementsListString.put(setName, new SimpleStringProperty(builder.toString()));
    }

    public Map<String, List<LegoTableSetItem>> getLegoTableSetItemMap() {
        return elementsList;
    }

    public int getTotalNumOfPartsInSets(Collection<String> setNames) {
        int amount = 0;
        for (String setName : setNames) {
            if (elementsList.containsKey(setName)) {
                for (LegoTableSetItem ltsi : elementsList.get(setName)) {
                    amount += ltsi.getQuantity();
                }
            }
        }
        return amount;
    }

    public int getDiff() {
        return diff.get();
    }

    public IntegerProperty diffProperty() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff.set(diff);
    }
}
