package com.example.project_inf201;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ServerHomePageController {
    Database database = Database.getInstance();

    @FXML
    private Button ChooseButton;

    @FXML
    private Button BackButton;

    @FXML
    private TextArea StudentsField;

    @FXML
    private ComboBox<String> Universities;

    @FXML
    void initialize() {
        assert ChooseButton != null : "fx:id=\"ChooseButton\" was not injected: check your FXML file 'ServerHomePage.fxml'.";
        assert StudentsField != null : "fx:id=\"StudentsField\" was not injected: check your FXML file 'ServerHomePage.fxml'.";
        assert Universities != null : "fx:id=\"Universities\" was not injected: check your FXML file 'ServerHomePage.fxml'.";
        ChooseButton.setOnMouseEntered(event -> ChooseButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        ChooseButton.setOnMouseExited(event -> ChooseButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        BackButton.setOnMouseEntered(event -> BackButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        BackButton.setOnMouseExited(event -> BackButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        StudentsField.appendText("Students:");
        forComboBox();
        ChooseButton.setOnAction(event -> {
            takeInformation();
        });
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(),"ServerLoginPage.fxml");
        });

    }
    void takeInformation(){
        String universityName = Universities.getValue();
        String res = database.selectServiceHomePage(universityName);
        StudentsField.setText("Students:\n" + res);
    }
    void forComboBox(){
        String res = database.selectServiceHomePageComboBox();
        if(res.equals("empty")){
            ExecutionProcedures.load(new Stage(), "universityDoesntExistsPage.fxml");
            Stage stage = (Stage) ChooseButton.getScene().getWindow();
            stage.close();
        }
        else{
            String[] data = res.split(", ");
            Universities.getItems().addAll(data);
        }
    }
}
