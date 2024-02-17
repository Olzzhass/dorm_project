package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainPageController {

    @FXML
    private Button StudentButton;

    @FXML
    private Button UniversityButton;

    @FXML
    void initialize() {
        assert StudentButton != null : "fx:id=\"StudentButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert UniversityButton != null : "fx:id=\"UniversityButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        StudentButton.setOnMouseEntered(event -> StudentButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        StudentButton.setOnMouseExited(event -> StudentButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));
        UniversityButton.setOnMouseEntered(event -> UniversityButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        UniversityButton.setOnMouseExited(event -> UniversityButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));

        StudentButton.setOnAction(event -> {
            Stage stage = (Stage) StudentButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentLoginPage.fxml");
        });
        UniversityButton.setOnAction(event -> {
            Stage stage = (Stage) StudentButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "UniversityLoginPage.fxml");
        });
    }

}
