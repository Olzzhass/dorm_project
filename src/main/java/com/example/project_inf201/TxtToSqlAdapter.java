package com.example.project_inf201;

import java.io.*;

public class TxtToSqlAdapter implements FileAdapter {
    String universityName;
    public void universityName(String name){
        universityName = name;
    }
    public void convertTo(File file, String from, String to) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] data = line.split(" - ");
                String room = data[0];
                int capacity = Integer.parseInt(data[1]);
                Client.sendMessageToServer("insert university: " + universityName + " - " + room + " - " + capacity);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
