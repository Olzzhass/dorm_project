package com.example.project_inf201;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UniversityRegistrationPageController {

    @FXML
    private TextField ConfirmationCode;

    @FXML
    private Button SendButton;

    @FXML
    private Button infoButton;

    @FXML
    private Line file_Indicator;

    @FXML
    private Button LoginButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button chooseFile;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Line password_Indicator;

    @FXML
    private TextField universityName;

    @FXML
    void initialize() {
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert SignUpButton != null : "fx:id=\"SignUpButton\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert chooseFile != null : "fx:id=\"chooseFile\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert password_Indicator != null : "fx:id=\"password_Indicator\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        assert universityName != null : "fx:id=\"universityName\" was not injected: check your FXML file 'UniversityRegistrationPage.fxml'.";
        LoginButton.setOnMouseEntered(event -> LoginButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        LoginButton.setOnMouseExited(event -> LoginButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        chooseFile.setOnMouseEntered(event -> chooseFile.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        chooseFile.setOnMouseExited(event -> chooseFile.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        SendButton.setOnMouseEntered(event -> SendButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SendButton.setOnMouseExited(event -> SendButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        SignUpButton.setOnMouseEntered(event -> SignUpButton.setStyle("-fx-background-color: #224C8D; -fx-background-radius: 50"));
        SignUpButton.setOnMouseExited(event -> SignUpButton.setStyle("-fx-background-radius: 50; -fx-background-color: #57A6DA"));
        passwordField.setOnKeyReleased(event -> {
            if(passwordField.getText().length()>15 || passwordField.getText().length()<8) {
                password_Indicator.setStroke(Color.RED);
            }
            else {
                password_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });

        infoButton.setOnAction(event -> ExecutionProcedures.load(new Stage(), "infoPage.fxml"));
        final File[] file = new File[1];
        chooseFile.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Dormitory Information File");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
            File selectedFile = chooser.showOpenDialog(new Stage());
            if(selectedFile != null){
                file[0] = selectedFile;
                file_Indicator.setStroke(Color.rgb(87, 166, 218));
            }
        });
        String confirmCode = sender.randomConfirmationCode();
        SignUpButton.setOnAction(event -> {
            String checkEmail = ExecutionProcedures.checkEmail("university", email.getText());
            if(!checkFile(file[0])){
                ExecutionProcedures.load(new Stage(), "invalidFilePage.fxml");
            }
            else if(checkEmail.equals("true")){
                ExecutionProcedures.load(new Stage(), "emailAlreadyExistsPage.fxml");
            }
            else if(checkUniversity().equals("true")){
                ExecutionProcedures.load(new Stage(), "universityAlreadyExistsPage.fxml");
            }
            else {
                chooseFile.setVisible(false);
                infoButton.setVisible(false);
                file_Indicator.setVisible(false);
                email.setVisible(false);
                universityName.setVisible(false);
                passwordField.setVisible(false);
                SignUpButton.setVisible(false);
                password_Indicator.setVisible(false);
                //
                ConfirmationCode.setVisible(true);
                SendButton.setVisible(true);
                sentMessageToUniver(confirmCode);
                ExecutionProcedures.load(new Stage(), "emailMessagePage.fxml");
            }
        });
        SendButton.setOnAction(event ->{
            String writtenByUniver = ConfirmationCode.getText();
            if(confirmCode.equals(writtenByUniver)){
                createTableOfThisUniversity();
                insertToUniversityTable(file[0]);
                Client.university_email = email.getText();
                Stage stage = (Stage) SignUpButton.getScene().getWindow();
                stage.close();
                ExecutionProcedures.load(new Stage(), "UniversityHomePage.fxml");
            }
            else{
                ExecutionProcedures.load(new Stage(), "invalidCodePage.fxml");
            }
        });
        LoginButton.setOnAction(event -> {
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "UniversityLoginPage.fxml");
        });
    }
    EmailSender sender = new EmailSender();
    boolean checkFile(File file) {
        boolean check = true;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String[] arr;
            int lineIdx = 1;
            while((line = reader.readLine()) != null){
                if(lineIdx == 1) {
                    arr = line.split(":");
                    if (arr[0].trim().isEmpty() || (!arr[0].trim().equals("University name")) || arr[1].trim().isEmpty()) {
                        check = false;
                    }
                }
                else {
                    arr = line.split(" - ");
                    if(!line.isEmpty()) {
                        if (arr[0].isEmpty() || arr[1].isEmpty()) {
                            check = false;
                        }
                        if (arr.length > 2) {
                            check = false;
                        }
                    }
                    try {
                        Integer.parseInt(arr[0]);
                        Integer.parseInt(arr[1]);
                    } catch (Exception e) {
                        check = false;
                    }
                }
                lineIdx++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
    String checkUniversity(){
        String univerName = universityName.getText();
        String data = "check university: " + univerName;
        Client.sendMessageToServer(data);
        return Client.fromServer;
    }
    void createTableOfThisUniversity(){
        String university_Email = email.getText();
        String university_Name = universityName.getText();
        String university_Password = passwordField.getText();
        Client.sendMessageToServer("create university: " + university_Email + ", " + university_Name + ", " + university_Password);
    }
    void insertToUniversityTable(File file) {
        TxtToSqlAdapter adapter = new TxtToSqlAdapter();
        adapter.universityName(universityName.getText());
        adapter.convertTo(file, "txt", "sql");
    }
    void sentMessageToUniver(String code) {
        String univerEmail = email.getText();
        sender.sendMessageToEmail(univerEmail, code);
    }
}

