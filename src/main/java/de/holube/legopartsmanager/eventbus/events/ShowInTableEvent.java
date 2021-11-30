package de.holube.legopartsmanager.eventbus.events;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.lego.LegoTableItem;

import java.util.List;

public class ShowInTableEvent implements Event {

    private final List<LegoTableItem> elements;

    public ShowInTableEvent(List<LegoTableItem> elements) {
        this.elements = elements;
    }

    public List<LegoTableItem> getElements() {
        return elements;
    }
}
