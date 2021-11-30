package de.holube.legopartsmanager.eventbus;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private final static List<EventBusSubscriber> subscribers = new ArrayList<>();

    public static void post(Event event) {
        for (EventBusSubscriber subscriber : subscribers) {
            subscriber.getEvent(event);
        }
    }

    public static void subscribe(EventBusSubscriber subscriber) {
        subscribers.add(subscriber);
    }
}
