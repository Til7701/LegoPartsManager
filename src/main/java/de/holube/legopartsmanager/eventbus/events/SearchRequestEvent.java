package de.holube.legopartsmanager.eventbus.events;

import de.holube.legopartsmanager.eventbus.Event;

public class SearchRequestEvent implements Event {
    private String text;

    public SearchRequestEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
