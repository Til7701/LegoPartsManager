package de.holube.legopartsmanager;

import de.holube.legopartsmanager.eventbus.Event;
import de.holube.legopartsmanager.eventbus.EventBus;
import de.holube.legopartsmanager.eventbus.EventBusSubscriber;
import de.holube.legopartsmanager.eventbus.events.SearchRequestEvent;
import de.holube.legopartsmanager.eventbus.events.ShowInTableEvent;
import de.holube.legopartsmanager.io.LegoFileWriter;
import de.holube.legopartsmanager.lego.LegoTableItem;
import de.holube.legopartsmanager.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController implements EventBusSubscriber {

    private final List<String> setNames = new ArrayList<>();
    private final Set<String> selectedSetNames = new HashSet<>();
    private final Map<String, CheckBox> setCheckBoxes = new HashMap<>();
    private final ObservableList<LegoTableItem> allItems = FXCollections.observableArrayList();
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
    private ObservableList<LegoTableItem> showedItems = FXCollections.observableArrayList();

    @FXML
    public void onSearchButtonPressed(ActionEvent actionEvent) {
        filter();
    }

    @FXML
    void onSearchBoxTyped() {
        filter();
    }

    @FXML
    public void onToBuy() {
        filter();
    }

    private void filter() {
        if (toBuyCheckBox.isSelected() || !searchTextField.getText().isBlank()) {
            if (!searchTextField.getText().isBlank()) {
                showedItems = FXCollections.observableArrayList(allItems.stream().filter(item -> (item.descriptionProperty().getValue() != null
                        && item.descriptionProperty().getValue().toLowerCase().contains(searchTextField.getText().toLowerCase()))
                        || item.designIDProperty().getValue().contains(searchTextField.getText())).toList()
                );
            } else {
                showedItems = allItems;
            }

            if (toBuyCheckBox.isSelected()) {
                showedItems = FXCollections.observableArrayList(showedItems.stream().filter(item -> item.diffProperty().getValue() < 0).collect(Collectors.toList()));
            }

        } else {
            showedItems = allItems;
        }

        itemCountLabel.setText(String.valueOf(showedItems.size()));
        mainTableView.setItems(showedItems);
        mainTableView.refresh();
    }

    @Override
    public void getEvent(Event event) {
        if (event instanceof ShowInTableEvent e) {
            setNames.addAll(e.getSetList());
            selectedSetNames.addAll(e.getSetList());
            allItems.setAll(e.getDesigns());

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
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(actionEvent -> onSetCheckBox(actionEvent, setName));
                checkBox.selectedProperty().set(true);
                setCheckBoxes.put(setName, checkBox);
                c.setGraphic(checkBox);
                c.setCellValueFactory(data -> data.getValue().getElements(setName));
                newSetColumns.add(c);
            }
            setColumn.getColumns().setAll(newSetColumns);
            diffColumn.setCellValueFactory(data -> data.getValue().diffProperty());
            calculateDiff();

            mainTableView.setItems(allItems);
            itemCountLabel.setText(String.valueOf(allItems.size()));
        }
    }

    private void onSetCheckBox(ActionEvent event, String setName) {
        if (setCheckBoxes.get(setName).isSelected()) selectedSetNames.add(setName);
        else selectedSetNames.remove(setName);

        calculateDiff();
    }

    private void calculateDiff() {
        allItems.forEach(legoTableItem -> {
            int amountInSelectedSets = legoTableItem.getTotalNumOfPartsInSets(selectedSetNames);
            legoTableItem.setDiff(legoTableItem.getOwn() - amountInSelectedSets);
        });
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

    public void onSaveButtonPressed() {
        Log.debug("Saving " + showedItems.size() + " lines");
        List<String> linesToSave = new ArrayList<>(showedItems.size() + 1);
        String columnSeparator = ",";

        linesToSave.add("DesignID,Description,Own,Difference\n");

        for (LegoTableItem legoTableItem : showedItems) {
            StringBuilder str = new StringBuilder(legoTableItem.getDesignID() + columnSeparator +
                    "\"" + legoTableItem.getDescription() + "\"" + columnSeparator +
                    legoTableItem.getOwn() + columnSeparator +
                    legoTableItem.getDiff());
            for (String setName : setNames) {
                str.append(columnSeparator).append(legoTableItem.getElements(setName).getValue().replace("\n", "; "));
            }
            linesToSave.add(str.toString());
        }

        try {
            LegoFileWriter.saveLinesToFile(linesToSave, "export.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}