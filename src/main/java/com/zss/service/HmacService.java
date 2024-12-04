package com.zss.service;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class HmacService {

    public static String generateHmacSha1(String keyHex, String message) {
        try {
            byte[] keyBytes = hexStringToByteArray(keyHex);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);

            byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania HMAC-SHA1", e);
        }
    }

    private static byte[] hexStringToByteArray(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}