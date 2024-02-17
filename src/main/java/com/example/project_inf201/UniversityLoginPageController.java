package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class UniversityLoginPageController {

    @FXML
    private Button BackButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField email;

    @FXML
    private Line login_Indicator;

    @FXML
    private Label Forgot_password;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Line password_Indicator;

    @FXML
    void initialize() {
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        assert SignUpButton != null : "fx:id=\"SignUpButton\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        assert email != null : "fx:id=\"loginField\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        assert login_Indicator != null : "fx:id=\"login_Indicator\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        assert password_Indicator != null : "fx:id=\"password_Indicator\" was not injected: check your FXML file 'UniversityLoginPage.fxml'.";
        LoginButton.setOnMouseEntered(event -> LoginButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        LoginButton.setOnMouseExited(event -> LoginButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        SignUpButton.setOnMouseEntered(event -> SignUpButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SignUpButton.setOnMouseExited(event -> SignUpButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        BackButton.setOnMouseEntered(event -> BackButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        BackButton.setOnMouseExited(event -> BackButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        Forgot_password.setOnMouseEntered(event -> Forgot_password.setTextFill(Color.rgb(34, 76, 141)));
        Forgot_password.setOnMouseExited(event -> Forgot_password.setTextFill(Color.WHITE));
        email.setOnKeyReleased(event -> {
            if(email.getText().length()>30) {
                login_Indicator.setStroke(Color.RED);
            }
            else {
                login_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });
        passwordField.setOnKeyReleased(event -> {
            if(passwordField.getText().length()>15 || passwordField.getText().length()<8) {
                password_Indicator.setStroke(Color.RED);
            }
            else {
                password_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });

        LoginButton.setOnAction(event -> {
            String checkEmail = ExecutionProcedures.checkEmail("university", email.getText());
            if (checkEmail.equals("true")){
                String checkPassword = ExecutionProcedures.checkPassword("university", email.getText(), passwordField.getText());
                if(checkPassword.equals("true")){
                    Client.university_email = email.getText();
                    Stage stage = (Stage) LoginButton.getScene().getWindow();
                    stage.close();
                    ExecutionProcedures.load(new Stage(), "UniversityHomePage.fxml");
                }
                else{
                    ExecutionProcedures.load(new Stage(), "invalidPasswordPage.fxml");
                }
            }
            else{
                ExecutionProcedures.load(new Stage(), "invalidEmailPage.fxml");
            }
        });
        BackButton.setOnAction(event ->{
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "MainPage.fxml");
        });
        SignUpButton.setOnAction(event -> {
            Stage stage = (Stage) SignUpButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "UniversityRegistrationPage.fxml");
        });
        Forgot_password.setOnMouseClicked(event -> {
            Stage stage = (Stage) Forgot_password.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "UniversityForgotPassword.fxml");
        });
    }
}
