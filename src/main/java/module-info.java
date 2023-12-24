module de.holube.legopartsmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens de.holube.legopartsmanager to javafx.graphics, javafx.fxml, javafx.base;
    exports de.holube.legopartsmanager;
    exports de.holube.legopartsmanager.eventbus;
}