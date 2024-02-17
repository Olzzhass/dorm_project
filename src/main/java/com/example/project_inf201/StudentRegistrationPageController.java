package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class StudentRegistrationPageController {

    @FXML
    private Button SendButton;

    @FXML
    private TextField ConfirmationCode;

    @FXML
    private Button LoginButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField StudentName;

    @FXML
    private ComboBox<String> University;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Line password_Indicator;

    @FXML
    void initialize() {
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert SignUpButton != null : "fx:id=\"SignUpButton\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert StudentName != null : "fx:id=\"StudentName\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert University != null : "fx:id=\"University\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        assert password_Indicator != null : "fx:id=\"password_Indicator\" was not injected: check your FXML file 'StudentRegistrationPage.fxml'.";
        LoginButton.setOnMouseEntered(event -> LoginButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        LoginButton.setOnMouseExited(event -> LoginButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        SignUpButton.setOnMouseEntered(event -> SignUpButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SignUpButton.setOnMouseExited(event -> SignUpButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        SendButton.setOnMouseEntered(event -> SendButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SendButton.setOnMouseExited(event -> SendButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        passwordField.setOnKeyReleased(event -> {
            if(passwordField.getText().length()>15 || passwordField.getText().length()<8) {
                password_Indicator.setStroke(Color.RED);
            }
            else {
                password_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });

        forComboBox();
        String confirmationCode = sender.randomConfirmationCode();
        SignUpButton.setOnAction(event->{
            String checkEmail = ExecutionProcedures.checkEmail("student", email.getText());
            if(checkEmail.equals("true")){
                ExecutionProcedures.load(new Stage(), "emailAlreadyExistsPage.fxml");
            }
            else {
                University.setVisible(false);
                StudentName.setVisible(false);
                email.setVisible(false);
                passwordField.setVisible(false);
                SignUpButton.setVisible(false);
                password_Indicator.setVisible(false);

                ConfirmationCode.setVisible(true);
                SendButton.setVisible(true);
                sentMessageToStudent(confirmationCode);
                ExecutionProcedures.load(new Stage(), "emailMessagePage.fxml");
            }

        });
        SendButton.setOnAction(event -> {
            String codeWritenByStudent = ConfirmationCode.getText();
            if(codeWritenByStudent.equals(confirmationCode)){
                saveEmail();
                insertToTableStudents();
                Stage stage = (Stage) SignUpButton.getScene().getWindow();
                stage.close();
                ExecutionProcedures.load(new Stage(), "StudentHomePage.fxml");
            }
            else{
                ExecutionProcedures.load(new Stage(),"invalidCodePage.fxml");
            }
        });
        LoginButton.setOnAction(event -> {
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentLoginPage.fxml");
        });
    }
    EmailSender sender = new EmailSender();
    void saveEmail() {
        Client.student_email = email.getText();
    }
    void insertToTableStudents()  {
        String Email = email.getText();
        String[] nameSurname = StudentName.getText().split(" ");
        String name = nameSurname[0];
        String surname = nameSurname[1];
        String university = University.getValue();
        String password = passwordField.getText();
        String data = "insert students: " + Email + ", " + name + ", " + surname + ", " + university + ", " + password;
        Client.sendMessageToServer(data);
    }
    void forComboBox(){
        String data = "for student registration page";
        Client.sendMessageToServer(data);
        String mes = Client.fromServer;
        String[] res = mes.split(", ");
        University.getItems().addAll(res);
    }
    void sentMessageToStudent(String code) {
        String studEmail = email.getText();
        sender.sendMessageToEmail(studEmail, code);
    }
}
