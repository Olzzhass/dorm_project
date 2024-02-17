package com.example.project_inf201;

import java.io.*;
import java.net.*;
import java.security.*;

public class Server implements ServerConnection{
    static ServerSocket serverSocket;
    static Socket socket;
    static Database database = Database.getInstance();
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(1024);
            System.out.println("Waiting clients message...");
            while (true){
                socket = serverSocket.accept();
                new Thread(new HandleClient(socket)).start();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static class HandleClient implements Runnable{
        Socket socket;
        public HandleClient(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                //BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //while (true){
                database.getSocket(socket);
                String fromClient = ServerConnection.getMessage(socket);
                System.out.println(fromClient);
                String[] message = fromClient.split(": ");
                switch (message[1]) {
                    case "create university": {
                        String[] data = message[2].split(", ");
                        String universityEmail = data[0];
                        String universityName = data[1];
                        String universityPassword = DataHashing.makeHashPassword(data[2], "SHA-256");
                        database.addToUniversitiesTable(universityEmail,universityName, universityPassword);
                        database.createUniversityTable(universityName);
                        break;
                    }
                    case "insert students": {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String name = data[1];
                        String surname = data[2];
                        String university = data[3];
                        String password = DataHashing.makeHashPassword(data[4], "SHA-256");
                        database.insertToStudentsTable(email, name, surname, university, password);
                        break;
                    }
                    case "insert university": {
                        String[] data = message[2].split(" - ");
                        String universityName = data[0];
                        String room = data[1];
                        int capacity = Integer.parseInt(data[2]);
                        database.insertToUniversityTable(universityName, room, capacity);
                        break;
                    }
                    case "update students": {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String room = data[1];
                        database.updateStudentsTable(email, room);
                        ServerConnection.sendMessage(socket, "true");
                        break;
                    }
                    case "update university": {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String room = data[1];
                        database.updateUniversityTable(email, room);
                        ServerConnection.sendMessage(socket, "true");
                        break;
                    }
                    case "for student registration page" : {
                        String res = database.selectStudentRegistrationPage();
                        if(!res.isEmpty()){
                            ServerConnection.sendMessage(socket, res);
                        }
                        else{
                            ServerConnection.sendMessage(socket, "empty");
                        }
                        break;
                    }
                    case "for student homepage" : {
                        String email = message[2];
                        String res = database.selectStudentHomePage(email);
                        ServerConnection.sendMessage(socket, res);
                        break;
                    }
                    case "for student roompage" : {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String room = data[1];
                        String res = database.selectStudentRoomPage(email, room);
                        ServerConnection.sendMessage(socket, res);
                        break;
                    }
                    case "for university homepage" : {
                        String email = message[2];
                        String res = database.selectUniversityHomePage(email);
                        ServerConnection.sendMessage(socket, res);
                        break;
                    }
                    case "check student email" : {
                        String email = message[2];
                        boolean check = database.checkStudentEmail(email);
                        if(check){
                            ServerConnection.sendMessage(socket, "true");
                        }
                        else{
                            ServerConnection.sendMessage(socket, "false");
                        }
                        break;
                    }
                    case "check student password" : {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String password = data[1];
                        String hashPassword = DataHashing.makeHashPassword(password, "SHA-256");
                        boolean check = database.checkStudentPassword(email, hashPassword);
                        if(check){
                            ServerConnection.sendMessage(socket, "true");
                        }
                        else{
                            ServerConnection.sendMessage(socket, "false");
                        }
                        break;
                    }
                    case "check university email" : {
                        String email = message[2];
                        boolean check = database.checkUniversityEmail(email);
                        if(check){
                            ServerConnection.sendMessage(socket, "true");
                        }
                        else{
                            ServerConnection.sendMessage(socket, "false");
                        }
                        break;
                    }
                    case "check university" : {
                        String univerName = message[2];
                        boolean check = database.checkUniversity(univerName);
                        if(check){
                            ServerConnection.sendMessage(socket, "true");
                        }
                        else{
                            ServerConnection.sendMessage(socket, "false");
                        }
                        break;
                    }
                    case "check university password" : {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String password = data[1];
                        String hashPassword = DataHashing.makeHashPassword(password, "SHA-256");
                        boolean check = database.checkUniversityPassword(email, hashPassword);
                        if(check){
                            ServerConnection.sendMessage(socket, "true");
                        }
                        else{
                            ServerConnection.sendMessage(socket, "false");
                        }
                        break;
                    }
                    case "forgot student password" : {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String newPassword = DataHashing.makeHashPassword(data[1], "SHA-256");
                        database.studentForgotPassword(email, newPassword);
                        break;
                    }
                    case "forgot university password" : {
                        String[] data = message[2].split(", ");
                        String email = data[0];
                        String newPassword = DataHashing.makeHashPassword(data[1], "SHA-256");
                        database.universityForgotPassword(email, newPassword);
                        break;
                    }
                    //}
                }
            } catch (IOException | NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
