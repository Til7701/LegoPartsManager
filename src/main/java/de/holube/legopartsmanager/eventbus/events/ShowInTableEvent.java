package de.holube.legopartsmanager.eventbus.events;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.lego.LegoTableItem;

import java.util.List;

public class ShowInTableEvent implements Event {

    private final List<LegoTableItem> designs;
    private final List<String> setList;

    public ShowInTableEvent(List<LegoTableItem> elements, List<String> setList) {
        this.setList = setList;
        this.designs = elements;
    }

    public List<LegoTableItem> getDesigns() {
        return designs;
    }

    public List<String> getSetList() {
        return setList;
    }
}
