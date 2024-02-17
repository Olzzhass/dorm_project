package com.example.project_inf201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentHomePageController {

    @FXML
    private Button BackButton;

    @FXML
    private Button NextButton;

    @FXML
    private Label RoomNumber;

    @FXML
    private Label StudentName;

    @FXML
    private ComboBox<String> Rooms;

    @FXML
    private Label UniversityName;

    @FXML
    void initialize() {
        assert NextButton != null : "fx:id=\"NextButton\" was not injected: check your FXML file 'StudentHomePage.fxml'.";
        assert RoomNumber != null : "fx:id=\"RoomNumber\" was not injected: check your FXML file 'StudentHomePage.fxml'.";
        assert StudentName != null : "fx:id=\"StudentName\" was not injected: check your FXML file 'StudentHomePage.fxml'.";
        assert Rooms != null : "fx:id=\"University\" was not injected: check your FXML file 'StudentHomePage.fxml'.";
        assert UniversityName != null : "fx:id=\"UniversityName\" was not injected: check your FXML file 'StudentHomePage.fxml'.";
        NextButton.setOnMouseEntered(event -> NextButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        NextButton.setOnMouseExited(event -> NextButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));
        BackButton.setOnMouseEntered(event -> BackButton.setStyle("-fx-background-color: #57A6DA; -fx-background-radius: 50"));
        BackButton.setOnMouseExited(event -> BackButton.setStyle("-fx-background-radius: 50; -fx-background-color: #9BE6FD"));

        takeInformationForHomePage();
        NextButton.setOnAction(event -> {
            giveInformationAboutRoom();
            Stage stage = (Stage) NextButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentRoomPage.fxml");
        });
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            ExecutionProcedures.load(new Stage(), "StudentLoginPage.fxml");
        });
    }

    public void takeInformationForHomePage() {
        String email = Client.student_email;
        String data = "for student homepage: " + email;
        Client.sendMessageToServer(data);
        String[] res = Client.fromServer.split(", ");
        String name = res[0];
        String surname = res[1];
        String university = res[2];
        String room = res[3];
        StudentName.setText("Student : " + name + " " + surname);
        UniversityName.setText("University : " + university);
        if(room.equals("null")){
            RoomNumber.setText("Room number : Not chosen");
            for (int i = 4; i < res.length; i++) {
                Rooms.getItems().add(res[i]);
            }
        }
        else{
            RoomNumber.setText("Room number : №" + room);
            Rooms.setVisible(false);
            NextButton.setVisible(false);
        }
    }
    void giveInformationAboutRoom() {
        String email = Client.student_email;
        String room = Rooms.getValue();
        String data = "for student roompage: " + email + ", " + room;
        Client.sendMessageToServer(data);
    }
}

