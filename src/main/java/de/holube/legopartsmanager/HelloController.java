package de.holube.legopartsmanager;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.EventBusSubscriber;
import de.holube.legopartsmanager.eventbus.events.SearchRequestEvent;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.lego.LegoTableItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class HelloController implements EventBusSubscriber {

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField imageSizeField;

    @FXML
    private TableView<LegoTableItem> mainTableView;

    @FXML
    private TableColumn<LegoTableItem, Image> imageColumn;

    @FXML
    private ImageView tableImageView;

    @FXML
    private TableColumn<LegoTableItem, String> designIDColumn;

    @FXML
    private TableColumn<LegoTableItem, Number> colorIDColumn;

    @FXML
    private TableColumn<LegoTableItem, String> elementIDColumn;

    @FXML
    private TableColumn<LegoTableItem, String> descriptionColumn;

    @FXML
    private TableColumn<LegoTableItem, Number> titanicColumn;

    @FXML
    private TableColumn<LegoTableItem, Number> ownColumn;

    private final ObservableList<LegoTableItem> items = FXCollections.observableArrayList();


    public void onSearchButtonPressed(ActionEvent actionEvent) {
        subscribe();
        EventBus.post(new SearchRequestEvent(searchTextField.getText()));
    }

    @Override
    public void getEvent(Event event) {
        if (event instanceof ShowInTableEvent) {
            ShowInTableEvent e = (ShowInTableEvent) event;
            items.setAll(e.getElements());

            imageColumn.setCellFactory(data -> {
                //Set up the ImageView
                final ImageView imageview = new ImageView();
                imageview.setFitHeight(Integer.parseInt(imageSizeField.getText()));
                imageview.setFitWidth(Integer.parseInt(imageSizeField.getText()));

                //Set up the Table
                TableCell<LegoTableItem, Image> cell = new TableCell<>() {
                    @Override
                    public void updateItem(Image item, boolean empty) {
                        if (item != null) {
                            imageview.setImage(item);
                        }
                    }
                };
                // Attach the imageview to the cell
                cell.setGraphic(imageview);
                return cell;
            });
            imageColumn.setCellValueFactory(data -> data.getValue().imageProperty());
            designIDColumn.setCellValueFactory(data -> data.getValue().designIDProperty());
            colorIDColumn.setCellValueFactory(data -> data.getValue().colorIDProperty());
            elementIDColumn.setCellValueFactory(data -> data.getValue().elementIDProperty());
            descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
            titanicColumn.setCellValueFactory(data -> data.getValue().titanicProperty());
            ownColumn.setCellValueFactory(data -> data.getValue().ownProperty());

            mainTableView.setItems(items);
        }
    }

    @Override
    public void subscribe() {
        EventBus.subscribe(this);
    }
}