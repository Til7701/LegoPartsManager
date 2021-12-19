package de.holube.legopartsmanager;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.EventBusSubscriber;
import de.holube.legopartsmanager.eventbus.events.SearchRequestEvent;
import de.holube.legopartsmanager.lego.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application implements EventBusSubscriber {

    private LegoSet titanicSet = new LegoSet("Titanic", "titanic.csv");
    private LegoSet falconSet = new LegoSet("Millennium Falcon", "millennium-falcon.csv");
    private LegoSet r2d2Set = new LegoSet("R2-D2", "r2-d2.csv");
    private OwnLegoSet ownSet = new OwnLegoSet("ist-aufstellung.csv");

    private LegoTableBuilder legoTableBuilder = new LegoTableBuilder();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Lego Parts Manager");
        stage.setScene(scene);
        stage.show();

        subscribe();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void getEvent(Event event) {
        if (event instanceof SearchRequestEvent) {
            List<LegoSet> setsToShow = new ArrayList<>();
            //setsToShow.add(titanicSet);
            //setsToShow.add(falconSet);
            setsToShow.add(r2d2Set);
            legoTableBuilder.prepareElementsInSets(setsToShow, ownSet);
        }
    }

    @Override
    public void subscribe() {
        EventBus.subscribe(this);
    }
}