package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class UniversityHomePageController {

    @FXML
    private Button BackButton;

    @FXML
    private TextArea StudentsInfo;

    @FXML
    private Label University;

    @FXML
    private Label available;

    @FXML
    private Label occupied;

    @FXML
    void initialize() {
        assert BackButton != null : "fx:id=\"BackButton\" was not injected: check your FXML file 'UniversityHomePage.fxml'.";
        assert StudentsInfo != null : "fx:id=\"StudentsInfo\" was not injected: check your FXML file 'UniversityHomePage.fxml'.";
        assert University != null : "fx:id=\"University\" was not injected: check your FXML file 'UniversityHomePage.fxml'.";
        assert available != null : "fx:id=\"available\" was not injected: check your FXML file 'UniversityHomePage.fxml'.";
        assert occupied != null : "fx:id=\"occupied\" was not injected: check your FXML file 'UniversityHomePage.fxml'.";
        BackButton.setOnMouseEntered(event -> BackButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        BackButton.setOnMouseExited(event -> BackButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));
        takeInformationForHomePage();
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "UniversityLoginPage.fxml");
        });
    }

    void takeInformationForHomePage() {
        String data = "for university homepage: " + Client.university_email;
        Client.sendMessageToServer(data);
        String[] res = Client.fromServer.split(", ");
        String university_name = res[0];
        String occupiedRate = res[1];
        String availableRate = res[2];
        University.setText("University : " + university_name);
        occupied.setText("Number of occupied places : " + occupiedRate);
        available.setText("Number of available places : " + availableRate);
        for (int i = 3; i < res.length; i++) {
            StudentsInfo.appendText(res[i] + "\n");
        }
    }
}

