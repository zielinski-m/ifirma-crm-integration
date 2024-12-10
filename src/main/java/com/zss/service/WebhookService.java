package com.zss.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Service
public class WebhookService {

    public static String sendToWebhook(String webhookUrl, String responseMessage) {
        StringBuilder jsonResponse = new StringBuilder();
        try {
            // Create HTTP connection
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // Data to JSON -> send to external webhook
            String jsonPayload = String.format("{\"response\": %s}", responseMessage);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                jsonResponse = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line).append("\n");
                    }
                }
            } else {
                System.err.println("Błąd: Kod odpowiedzi HTTP " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Błąd podczas sprawdzania limitu API: " + e.getMessage());
        }
        return jsonResponse.toString();
    }
}

