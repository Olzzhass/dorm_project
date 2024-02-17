package com.example.project_inf201;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public interface ServerConnection {
    static String getMessage(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return br.readLine();
    }
    static void sendMessage(Socket socket, String data) throws IOException {
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println(data);
    }
}
