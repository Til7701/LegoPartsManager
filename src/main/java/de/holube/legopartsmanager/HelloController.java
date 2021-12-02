package de.holube.legopartsmanager;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.EventBusSubscriber;
import de.holube.legopartsmanager.eventbus.events.SearchRequestEvent;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.lego.LegoTableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private TableColumn<LegoTableItem, String> designIDColumn;

    @FXML
    private TableColumn<LegoTableItem, String> descriptionColumn;

    @FXML
    private TableColumn<LegoTableItem, Number> ownColumn;

    @FXML
    private TableColumn<LegoTableItem, Number> setColumn;

    private final ObservableList<LegoTableItem> items = FXCollections.observableArrayList();


    public void onSearchButtonPressed(ActionEvent actionEvent) {
        subscribe();
        EventBus.post(new SearchRequestEvent(searchTextField.getText()));
    }

    @Override
    public void getEvent(Event event) {
        if (event instanceof ShowInTableEvent) {
            ShowInTableEvent e = (ShowInTableEvent) event;
            items.setAll(e.getDesigns());

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
            descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
            ownColumn.setCellValueFactory(data -> data.getValue().ownProperty());
            ObservableList<TableColumn<LegoTableItem, String>> newSetColumns = FXCollections.observableArrayList();
            for (String setName : e.getSetList()) {
                TableColumn<LegoTableItem, String> c = new TableColumn<>();
                c.setCellValueFactory(data -> data.getValue().getElements(setName));
                newSetColumns.add(c);
            }
            setColumn.getColumns().setAll(newSetColumns);

            mainTableView.setItems(items);
        }
    }

    @Override
    public void subscribe() {
        EventBus.subscribe(this);
    }
}