package com.example.project_inf201;

import java.io.*;
import java.net.*;

public class Client implements ServerConnection{
    static String student_email;
    static String university_email;
    static String fromServer;
    public static void sendMessageToServer(String message)  {
        try {
            // нужно написать host
            InetAddress host1 = InetAddress.getLocalHost();
            String host2 = "172.20.10.5";
            Socket socket = new Socket(host1, 1024);
            System.out.print("Client: ");
            System.out.println(message);
            String fromClient = "Client: " + message;
            ServerConnection.sendMessage(socket, fromClient);
            fromServer = ServerConnection.getMessage(socket);
            System.out.println(fromServer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }










    //public static void main(String[] args) {
    // Для проверки
//        Client.sendMessageToServer("insert students: rasul@gmail.com, Rassul, Saduakas, sdu, passWord");
//        TxtToSqlAdapter adapter = new TxtToSqlAdapter();
//        File file = new File("/Users/saduakasrasul/IdeaProjects/Project_INF201/src/universityDate.txt");
//        adapter.convertTo(file, "txt", "sql");

    //Используется как чат
//        Socket socket;
//        try {
//            System.out.println("Request sent successfully...");
//            InetAddress host = InetAddress.getLocalHost();
//            socket = new Socket(host,1024);
//            BufferedReader writtenByClient = new BufferedReader(new InputStreamReader(System.in));
//            while (true){
//                System.out.print("Client: ");
//                String fromClient = "Client: " + writtenByClient.readLine();
//                ServerConnection.sendMessage(socket, fromClient);
//                //System.out.println(ServerConnection.getMessage(socket));
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    //}
}
