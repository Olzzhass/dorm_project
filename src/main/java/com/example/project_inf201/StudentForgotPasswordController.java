package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class StudentForgotPasswordController {

    @FXML
    private TextField ConfirmationCode;

    @FXML
    private Button LoginButton;

    @FXML
    private Button RestorePassword;

    @FXML
    private Button SendButton;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Line password_Indicator;

    @FXML
    void initialize() {
        assert ConfirmationCode != null : "fx:id=\"ConfirmationCode\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert RestorePassword != null : "fx:id=\"RestorePassword\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert SendButton != null : "fx:id=\"SendButton\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert passwordField1 != null : "fx:id=\"passwordField1\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert passwordField2 != null : "fx:id=\"passwordField2\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";
        assert password_Indicator != null : "fx:id=\"password_Indicator\" was not injected: check your FXML file 'StudentForgotPassword.fxml'.";

        SendButton.setOnMouseEntered(event -> SendButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SendButton.setOnMouseExited(event -> SendButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        RestorePassword.setOnMouseEntered(event -> RestorePassword.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        RestorePassword.setOnMouseExited(event -> RestorePassword.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        LoginButton.setOnMouseEntered(event -> LoginButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        LoginButton.setOnMouseExited(event -> LoginButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        passwordField1.setOnKeyReleased(event -> {
            if(passwordField1.getText().length()>15 || passwordField1.getText().length()<8) {
                password_Indicator.setStroke(Color.RED);
            }
            else {
                password_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });
        passwordField2.setOnKeyReleased(event -> {
            if(passwordField2.getText().length()>15 || passwordField2.getText().length()<8) {
                password_Indicator.setStroke(Color.RED);
            }
            else {
                password_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });

        String confirmCode = sender.randomConfirmationCode();
        RestorePassword.setOnAction(event ->{
            String checkEmail = ExecutionProcedures.checkEmail("student", email.getText());
            if(checkEmail.equals("true")){
                if(passwordField1.getText().equals(passwordField2.getText())){
                    email.setVisible(false);
                    passwordField1.setVisible(false);
                    passwordField2.setVisible(false);
                    RestorePassword.setVisible(false);
                    password_Indicator.setVisible(false);
                    //
                    ConfirmationCode.setVisible(true);
                    SendButton.setVisible(true);
                    sentMessageToEmail(confirmCode);
                }
                else{
                    ExecutionProcedures.load(new Stage(), "passwordsDontMatchPage.fxml");
                }
            }
            else{
                ExecutionProcedures.load(new Stage(), "invalidEmailPage.fxml");
            }
        });
        LoginButton.setOnAction(event -> {
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentLoginPage.fxml");
        });
        SendButton.setOnAction(event -> {
            String writtenByClient = ConfirmationCode.getText();
            if(confirmCode.equals(writtenByClient)){
                updateStudentsTable();
                ExecutionProcedures.load(new Stage(), "passwordChangedPage.fxml");
            }
            else {
                ExecutionProcedures.load(new Stage(), "invalidCodePage.fxml");
            }
        });
    }
    EmailSender sender = new EmailSender();
    void sentMessageToEmail(String code) {
        String studentEmail = email.getText();
        sender.sendMessageToEmail(studentEmail, code);
    }
    void updateStudentsTable(){
        String data = "forgot student password: " + email.getText() + ", " + passwordField1.getText();
        Client.sendMessageToServer(data);
    }
}
