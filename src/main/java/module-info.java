module com.example.inventoryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventoryapp to javafx.fxml;
    exports com.example.inventoryapp;
    exports com.example.inventoryapp.controllers;
    opens com.example.inventoryapp.controllers to javafx.fxml;
}