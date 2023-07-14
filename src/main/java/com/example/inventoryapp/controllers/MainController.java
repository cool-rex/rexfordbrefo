package com.example.inventoryapp.controllers;

import com.example.inventoryapp.Inventory;
import com.example.inventoryapp.Main;
import com.example.inventoryapp.Part;
import com.example.inventoryapp.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part,Integer> partId;
    @FXML
    private TableColumn<Part,String> partName;
    @FXML
    private TableColumn<Part,Integer> partInvLevel;
    @FXML
    private TableColumn<Part,Double> partPrice;
    @FXML
    private TextField partSearchField;
    @FXML
    private Button partAddButton;
    @FXML
    private Button partModifyButton;
    @FXML
    private Button partDeleteButton;

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private Button productAddButton;
    @FXML
    private Button productModifyButton;
    @FXML
    private Button productDeleteButton;

    @FXML
    private TextField productSearchField;
    @FXML
    private Button exitButton;

    private Inventory inv;




    public MainController(Inventory inv) {
        this.inv = inv;
    }



    @FXML
    private void addPart(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneToAddPart();
    }
    @FXML
    private void exitButtonClick(ActionEvent event) {
        Main m = new Main();
        m.exit();
    }

    @FXML
    private void modifyPart(ActionEvent event) throws IOException {
        Main m = new Main();
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();

        if(partSelected != null) {
            m.changeSceneToModifyPart(partSelected);
        }
    }
    @FXML
    private void deletePart(ActionEvent event) throws IOException {
        Main m = new Main();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();
        alert.setContentText(String.format("Are you sure you want to delete the %s ?", partSelected.getName() ));

        if(partSelected != null) {
            alert.showAndWait();
            if(alert.getResult().getText().equals("OK")) {
               if(inv.deletePart(partSelected)) {
                   m.changeSceneToMain();
               }
            }
        }
    }


    @FXML
    private void filterTable(KeyEvent event) throws IOException {
        if(!event.getCode().equals(KeyCode.ENTER)) return;
        String searchString = partSearchField.getText().trim();
        Main m = new Main();
        if(searchString.isEmpty()) {
            m.changeSceneToMain();
            return;
        }
        try {
            int searchInt = Integer.parseInt(searchString);
            Part result = inv.lookupPart(searchInt);
            if(result == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText( String.format("Part with partId %s not found.", searchInt));
                alert.showAndWait();
                m.changeSceneToMain();
            } else {
                partsTable.setItems(FXCollections.observableArrayList(result));
                partsTable.refresh();
                return;
            }
        } catch (NumberFormatException | IOException ignored) {

        }
        searchString = searchString.toLowerCase(Locale.ROOT);
        partsTable.setItems(inv.lookupPart(searchString.trim()));
        partsTable.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partId.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));

        partsTable.setItems(inv.getAllParts());
        partsTable.refresh();

    }
}
