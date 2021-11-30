package de.holube.legopartsmanager.eventbus;

public interface EventBusSubscriber {

    void getEvent(Event event);

    void subscribe();
}
