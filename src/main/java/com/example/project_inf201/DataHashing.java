package com.example.project_inf201;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataHashing {
    public static String makeHashPassword(String data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(data.getBytes());
        byte[] hashedData = messageDigest.digest();
        // Преобразование байтового массива в строку в шестнадцатеричном формате
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedData) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
