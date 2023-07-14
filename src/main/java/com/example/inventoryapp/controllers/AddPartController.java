package com.example.inventoryapp.controllers;

import com.example.inventoryapp.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.EOFException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AddPartController {


    private Inventory inv;
    @FXML
    private Label changingLabel;


    @FXML
    private RadioButton inhouseRadioButton, outsourcedRadioButton;
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

    public AddPartController(Inventory inv) {
        this.inv = inv;
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

        Part part = new InHouse(generateId(), partName.getText().trim(),
                Double.parseDouble(partPrice.getText()), Integer.parseInt(partInv.getText()), Integer.parseInt(partMinInv.getText()),
                Integer.parseInt(partMaxInv.getText()),Integer.parseInt(partMachIdOrCompanyName.getText()));
        inv.addPart(part);
    }
    private void addOutsourced() {
        Part part = new Outsourced(generateId(), partName.getText().trim(),
                Double.parseDouble(partPrice.getText()), Integer.parseInt(partInv.getText()), Integer.parseInt(partMinInv.getText()),
                Integer.parseInt(partMaxInv.getText()),partMachIdOrCompanyName.getText());
        inv.addPart(part);
    }

    private int generateId() {
        return inv.getAllParts().size() + 1;
    }

    public void cancelPart(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeSceneToMain();
    }

}
