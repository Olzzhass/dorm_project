package com.example.project_inf201;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExecutionProcedures {
    static String checkEmail(String studentOrUniversity, String email){
        String data = "check " + studentOrUniversity +  " email: " + email;
        Client.sendMessageToServer(data);
        return Client.fromServer;
    }
    static String checkPassword(String studentOrUniversity, String email, String password){
        String data = "check " + studentOrUniversity + " password: " + email + ", " + password;
        Client.sendMessageToServer(data);
        return Client.fromServer;
    }
    static void load(Stage stage, String fxmlFileName){
        try {
            FXMLLoader loader = new FXMLLoader(ExecutionProcedures.class.getResource(fxmlFileName));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Dormitory Management System");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
