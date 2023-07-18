package com.example.inventoryapp;

import com.example.inventoryapp.controllers.AddPartController;
import com.example.inventoryapp.controllers.MainController;
import com.example.inventoryapp.controllers.ModifyPartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

import java.io.IOException;

/*
* Name: Rexford Brefo
* ID: 10917663
* */

public class Main extends Application {
    private static Stage mainStage;
    private static Inventory inv = new Inventory();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        inv.addPart(new InHouse(1,"car tyre", 9.99, 9, 1,10,0221));
        inv.addPart(new Outsourced(2,"gear",8.99,9,1,19,"MAN INC"));
        inv.addPart(new Outsourced(102,"tv stand",5.99,9,1,19,"MAN INC"));
        changeSceneToMain();
        stage.show();
    }
    public void changeSceneToAddPart() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        AddPartController controller = new AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        mainStage.setScene(new Scene(root,1020,600));
    }
    public void changeSceneToModifyPart(Part partSelected) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
        ModifyPartController controller = new ModifyPartController(partSelected, inv);

        loader.setController(controller);
        Parent root = loader.load();
        mainStage.setScene(new Scene(root,1020,600));
        mainStage.setTitle("Modify Part");
    }

    public void changeSceneToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        MainController controller = new MainController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        mainStage.setScene(new Scene(root, 1020, 440));
        mainStage.setTitle("Inventory Management System");

    }
    public void exit() { mainStage.close(); }


}