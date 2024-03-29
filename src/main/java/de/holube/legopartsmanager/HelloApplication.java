package de.holube.legopartsmanager;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.EventBusSubscriber;
import de.holube.legopartsmanager.eventbus.events.SearchRequestEvent;
import de.holube.legopartsmanager.lego.LegoSet;
import de.holube.legopartsmanager.lego.LegoTableBuilder;
import de.holube.legopartsmanager.lego.OwnLegoSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application implements EventBusSubscriber {

    private LegoSet titanicSet = new LegoSet("Titanic", "titanic.csv");
    //private LegoSet falconSet = new LegoSet("Millennium Falcon", "millennium-falcon.csv");
    //private LegoSet r2d2Set2012 = new LegoSet("R2-D2 2012", "r2-d2-2012.csv");
    //private LegoSet r2d2Set2021 = new LegoSet("R2-D2 2021", "r2-d2-2021.csv");
    //private LegoSet yodaSet = new LegoSet("Yoda", "yoda.csv");
    private LegoSet nightSet = new LegoSet("The Starry Night", "the-starry-night.csv");
    private LegoSet hogwartsSet = new LegoSet("Hogwarts", "hogwarts.csv");
    private OwnLegoSet ownSet = new OwnLegoSet("ist-aufstellung.csv");

    private LegoTableBuilder legoTableBuilder = new LegoTableBuilder();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Lego Parts Manager");
        stage.setScene(scene);
        stage.show();

        subscribe();
    }

    @Override
    public void getEvent(Event event) {
        if (event instanceof SearchRequestEvent) {
            List<LegoSet> setsToShow = new ArrayList<>();
            setsToShow.add(titanicSet);
            //setsToShow.add(falconSet);
            //setsToShow.add(r2d2Set2012);
            //setsToShow.add(r2d2Set2021);
            //setsToShow.add(yodaSet);
            setsToShow.add(nightSet);
            setsToShow.add(hogwartsSet);
            legoTableBuilder.prepareElementsInSets(setsToShow, ownSet);
        }
    }

    @Override
    public void subscribe() {
        EventBus.subscribe(this);
    }
}