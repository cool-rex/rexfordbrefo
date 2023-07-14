package com.example.inventoryapp.controllers;

import com.example.inventoryapp.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    private Inventory inv;
    private Part selectedPart;



    @FXML
    private RadioButton inhouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private Label changingLabel;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMaxInv;
    @FXML
    private TextField partMinInv;
    @FXML
    private TextField partMachIdOrCompanyName;

    public ModifyPartController(Part selectedPart, Inventory inv) {
       this.inv = inv;
       this.selectedPart = selectedPart;
    }
    public void toggleRadioButtons(ActionEvent event) throws IOException {
        if(inhouseRadioButton.isSelected()) {
            changingLabel.setText("Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            changingLabel.setText("Company Name");
        }
    }
    public void savePart(ActionEvent event) throws IOException {
        Main m = new Main();
        boolean isValid = true;
        try {
            Integer.parseInt(partInv.getText().trim());
            Integer.parseInt(partMaxInv.getText().trim());
            Integer.parseInt(partMinInv.getText().trim());
            Double.parseDouble(partPrice.getText().trim());
            if(partName.getText().trim().isEmpty()) throw new NumberFormatException();

            if(inhouseRadioButton.isSelected()){
                Integer.parseInt(partMachIdOrCompanyName.getText().trim());
            } else if (partMachIdOrCompanyName.getText().trim().isEmpty()) throw new NumberFormatException();

            if(Integer.parseInt(partMinInv.getText().trim()) >= Integer.parseInt(partMaxInv.getText().trim())) throw new NumberFormatException();

            if(Integer.parseInt(partInv.getText().trim()) > Integer.parseInt(partMaxInv.getText().trim()) ||
                    Integer.parseInt(partInv.getText().trim()) < Integer.parseInt(partMinInv.getText().trim())) throw new NumberFormatException();
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            StringBuilder alertMessage = new StringBuilder();
            isValid = false;
            try{
                Integer.parseInt(partInv.getText().trim());
            } catch(NumberFormatException ex) {
                alertMessage.append("Inv is not an integer!\n");
            }
            try {
                Integer.parseInt(partMaxInv.getText().trim());
            } catch(NumberFormatException ex) {
                alertMessage.append("Max is not an integer!\n");
            }
            try {
                Integer.parseInt(partMinInv.getText().trim());
            } catch(NumberFormatException ex) {
                alertMessage.append("Min is not an integer\n");
            }
            try {
                Double.parseDouble(partPrice.getText().trim());
            } catch(NumberFormatException ex) {
                alertMessage.append("Price is not a double!\n");
            }
            try {
                if(partName.getText().trim().isEmpty()) throw new NumberFormatException();
            } catch(NumberFormatException ex) {
                alertMessage.append("Name is empty!\n");
            }
            try {
                if(inhouseRadioButton.isSelected()){
                    Integer.parseInt(partMachIdOrCompanyName.getText().trim());
                }
            } catch (NumberFormatException ex) {
                alertMessage.append("Machine ID is not an integer!\n");
            }
            try {
                if(outsourcedRadioButton.isSelected() && partMachIdOrCompanyName.getText().trim().isEmpty()) throw new NumberFormatException();
            } catch(NumberFormatException ex) {
                alertMessage.append("Company Name is empty!\n");
            }
            try {
                if(Integer.parseInt(partMinInv.getText().trim()) >= Integer.parseInt(partMaxInv.getText().trim())) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                alertMessage.append("Min must be less than the Max!\n");
            }
            try {
                if(Integer.parseInt(partInv.getText().trim()) > Integer.parseInt(partMaxInv.getText().trim()) ||
                        Integer.parseInt(partInv.getText().trim()) < Integer.parseInt(partMinInv.getText().trim())) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                alertMessage.append("Inv must be equal to or between Max and Min values!\n");
            }
            alert.setContentText(alertMessage.toString());
            alert.setHeaderText("Incorrect Data!");
            alert.showAndWait();
        }
        if(isValid) {
            if(inhouseRadioButton.isSelected()) {
                addInhouse();
                m.changeSceneToMain();
            } else {
                addOutsourced();
                m.changeSceneToMain();
            }
        }
    }
    private void addInhouse() {

        Part part = new InHouse(selectedPart.getId(), partName.getText().trim(),
                Double.parseDouble(partPrice.getText()), Integer.parseInt(partInv.getText()), Integer.parseInt(partMinInv.getText()),
                Integer.parseInt(partMaxInv.getText()),Integer.parseInt(partMachIdOrCompanyName.getText()));
        int index = inv.getAllParts().indexOf(selectedPart);
        inv.updatePart(index,part);
    }
    private void addOutsourced() {
        Part part = new Outsourced(selectedPart.getId(), partName.getText().trim(),
                Double.parseDouble(partPrice.getText()), Integer.parseInt(partInv.getText()), Integer.parseInt(partMinInv.getText()),
                Integer.parseInt(partMaxInv.getText()),partMachIdOrCompanyName.getText());
        int index = inv.getAllParts().indexOf(selectedPart);
        inv.updatePart(index,part);
    }


    public void cancelPart(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneToMain();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(selectedPart instanceof InHouse) {
            InHouse part = (InHouse)selectedPart;
            inhouseRadioButton.setSelected(true);
            partMachIdOrCompanyName.setText(String.valueOf(part.getMachineId()));

        } else {
            Outsourced part = (Outsourced)selectedPart;
            partMachIdOrCompanyName.setText(part.getCompanyName());
           outsourcedRadioButton.setSelected(true);
        }

        partName.setText(selectedPart.getName());
        partInv.setText(String.valueOf(selectedPart.getStock()));
        partPrice.setText(String.valueOf(selectedPart.getPrice()));
        partMaxInv.setText(String.valueOf(selectedPart.getMax()));
        partMinInv.setText(String.valueOf(selectedPart.getMin()));


    }
}
