package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentRoomPageController {

    @FXML
    private Button BackButton;

    @FXML
    private Button ChooseButton;

    @FXML
    private Label RoomNumber;

    @FXML
    private Label cnt;

    @FXML
    private Button SendButton;

    @FXML
    private TextField ConfirmationCode;

    @FXML
    void initialize() {
        assert BackButton != null : "fx:id=\"BackButton\" was not injected: check your FXML file 'StudentRoomPage.fxml'.";
        assert ChooseButton != null : "fx:id=\"ChooseButton\" was not injected: check your FXML file 'StudentRoomPage.fxml'.";
        assert RoomNumber != null : "fx:id=\"RoomNumber\" was not injected: check your FXML file 'StudentRoomPage.fxml'.";
        assert cnt != null : "fx:id=\"cnt\" was not injected: check your FXML file 'StudentRoomPage.fxml'.";
        ChooseButton.setOnMouseEntered(event -> ChooseButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        ChooseButton.setOnMouseExited(event -> ChooseButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));
        SendButton.setOnMouseEntered(event -> SendButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        SendButton.setOnMouseExited(event -> SendButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));

        BackButton.setOnMouseEntered(event -> BackButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        BackButton.setOnMouseExited(event -> BackButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));

        takeInformationForRoomPage();
        String code = sender.randomConfirmationCode();
        ChooseButton.setOnAction(event -> {
            ChooseButton.setVisible(false);
            ConfirmationCode.setVisible(true);
            SendButton.setVisible(true);
            sentMessageToStudent(code);
            ExecutionProcedures.load(new Stage(), "emailMessagePage.fxml");
        });

        SendButton.setOnAction(event -> {
            String studCode = ConfirmationCode.getText();
            if(studCode.equals(code)){
                updateStudentsTable();
                updateUniversityTable();
                String occupied = cnt.getText();
                String[] data = occupied.split("/");
                int left = Integer.parseInt(data[0]);
                left = left + 1;
                data[0] = Integer.toString(left);
                cnt.setText(data[0] + "/" + data[1]);
                ConfirmationCode.setVisible(false);
                SendButton.setVisible(false);
            }
            else {
                ExecutionProcedures.load(new Stage(), "invalidCodePage.fxml");
            }
        });
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentHomePage.fxml");
        });
    }

    EmailSender sender = new EmailSender();
    void takeInformationForRoomPage() {
        String[] res = Client.fromServer.split(" - ");
        String room = res[0];
        RoomNumber.setText("Room : №" + room);
        String capacity = res[1];
        String count_of_students = res[2];
        cnt.setText(count_of_students + "/" + capacity);
        if(capacity.equals(count_of_students)){
            ChooseButton.setVisible(false);
        }
    }
    void updateStudentsTable(){
        String email = Client.student_email;
        String room = RoomNumber.getText().substring(RoomNumber.getText().length()-3);
        String data = "update students: " + email + ", " + room;
        Client.sendMessageToServer(data);
    }
    void updateUniversityTable(){
        String email = Client.student_email;
        String room = RoomNumber.getText().substring(RoomNumber.getText().length()-3);
        String data = "update university: " + email + ", " + room;
        Client.sendMessageToServer(data);
    }
    void sentMessageToStudent(String code) {
        String email = Client.student_email;
        sender.sendMessageToEmail(email, code);
    }
}
