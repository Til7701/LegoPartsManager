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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Collectors;

public class HelloController implements EventBusSubscriber {

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField imageSizeField;

    @FXML
    private Label itemCountLabel;

    @FXML
    private CheckBox toBuyCheckBox;

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

    @FXML
    private TableColumn<LegoTableItem, Number> diffColumn;

    private final ObservableList<LegoTableItem> items = FXCollections.observableArrayList();
    private ObservableList<LegoTableItem> filteredItems = FXCollections.observableArrayList();

    @FXML
    public void onSearchButtonPressed(ActionEvent actionEvent) {
        filter();
    }

    @FXML
    public void onToBuy() {
        filter();
    }

    private void filter() {
        if (toBuyCheckBox.isSelected() || !searchTextField.getText().isBlank()) {
            if (!searchTextField.getText().isBlank()) {
                filteredItems = FXCollections.observableArrayList(items.stream().filter(item -> (item.descriptionProperty().getValue() != null
                        && item.descriptionProperty().getValue().contains(searchTextField.getText()))
                        || item.designIDProperty().getValue().contains(searchTextField.getText())).collect(Collectors.toList())
                );
            } else {
                filteredItems = items;
            }

            if (toBuyCheckBox.isSelected()) {
                filteredItems = FXCollections.observableArrayList(filteredItems.stream().filter(item -> item.diffProperty().getValue() < 0).collect(Collectors.toList()));
            }

            mainTableView.setItems(filteredItems);
            itemCountLabel.setText(String.valueOf(filteredItems.size()));
        } else {
            mainTableView.setItems(items);
            itemCountLabel.setText(String.valueOf(items.size()));
        }

        mainTableView.refresh();
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
                c.textProperty().setValue(setName);
                c.setCellValueFactory(data -> data.getValue().getElements(setName));
                newSetColumns.add(c);
            }
            setColumn.getColumns().setAll(newSetColumns);
            diffColumn.setCellValueFactory(data -> data.getValue().diffProperty());

            mainTableView.setItems(items);
            itemCountLabel.setText(String.valueOf(items.size()));
        }
    }

    @Override
    public void subscribe() {
        EventBus.subscribe(this);
    }

    @FXML
    public void onLoadButtonPressed(ActionEvent actionEvent) {
        subscribe();
        EventBus.post(new SearchRequestEvent(searchTextField.getText()));
    }
}