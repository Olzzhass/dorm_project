package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerLoginPageController {

    @FXML
    private Button LoginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'ServerLoginPage.fxml'.";
        assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'ServerLoginPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'ServerLoginPage.fxml'.";
        LoginButton.setOnMouseEntered(event -> LoginButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        LoginButton.setOnMouseExited(event -> LoginButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        LoginButton.setOnAction(event -> {
            if(loginField.getText().equals("admin")){
                if(passwordField.getText().equals("admin")){
                    Stage stage = (Stage) LoginButton.getScene().getWindow();
                    stage.close();
                    ExecutionProcedures.load(new Stage(),"ServerHomePage.fxml");
                }
                else{
                    ExecutionProcedures.load(new Stage(),"invalidPasswordPage.fxml");
                }
            }
            else{
                ExecutionProcedures.load(new Stage(), "invalidLoginPage.fxml");
            }
        });
    }
}
