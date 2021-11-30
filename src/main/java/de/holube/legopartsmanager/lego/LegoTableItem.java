package de.holube.legopartsmanager.lego;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class LegoTableItem {

    private final Property<Image> image = new SimpleObjectProperty<>();
    private final StringProperty designID = new SimpleStringProperty();
    private final IntegerProperty colorID = new SimpleIntegerProperty();
    private final StringProperty elementID = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty titanic = new SimpleIntegerProperty();
    private final IntegerProperty own = new SimpleIntegerProperty();

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

    public int getColorID() {
        return colorID.get();
    }

    public IntegerProperty colorIDProperty() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID.set(colorID);
    }

    public String getElementID() {
        return elementID.get();
    }

    public StringProperty elementIDProperty() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID.set(elementID);
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

    public int getTitanic() {
        return titanic.get();
    }

    public IntegerProperty titanicProperty() {
        return titanic;
    }

    public void setTitanic(int titanic) {
        this.titanic.set(titanic);
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
}
