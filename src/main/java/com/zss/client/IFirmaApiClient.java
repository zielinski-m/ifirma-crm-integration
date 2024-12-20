package com.zss.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.zss.service.HmacService.generateHmacSha1;
import static com.zss.service.WebhookService.sendToWebhook;

@Component
public class IFirmaApiClient {

    private static StringBuilder jsonResponse;
    private static String requestContent;

    public static String fetchPaidInvoices(String userName, String keyNameFaktura,
                                           String symmetricalKeyFaktura, String apiUrlPaid) {
        try {
            requestContent = "";
            String baseUrl = apiUrlPaid.split("\\?")[0];
            String hashMessage = baseUrl + userName + keyNameFaktura + requestContent;
            String hmacHash = generateHmacSha1(symmetricalKeyFaktura, hashMessage);

            String authHeader = String.format("IAPIS user=%s, hmac-sha1=%s", userName, hmacHash);

            URL url = new URL(apiUrlPaid);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authentication", authHeader);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                jsonResponse = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line).append("\n");
                    }
                }
                sendToWebhook("https://n8n.melongroup.pl/webhook-test/database", jsonResponse.toString());
            } else {

                System.err.println("Błąd: Kod odpowiedzi HTTP " + responseCode);
                StringBuilder errorResponse = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd podczas pobierania opłaconych faktur: " + e.getMessage());
        }
        return String.valueOf(jsonResponse);
    }


    public static String checkApiLimit(String userName, String symmetricalKeyAbonent) {

        String apiUrl = "https://www.ifirma.pl/iapi/abonent/limit.json";
        String keyNameAbonent = "abonent";

        try {
            requestContent = ""; // *Important* Empty for GET request
            String baseUrl = apiUrl.split("\\?")[0]; // URL with no params
            String hashMessage = baseUrl + userName + keyNameAbonent + requestContent;

            String hmacHash = generateHmacSha1(symmetricalKeyAbonent, hashMessage);

            String authHeader = String.format("IAPIS user=%s, hmac-sha1=%s", userName, hmacHash);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authentication", authHeader);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                jsonResponse = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Błąd podczas sprawdzania limitu API: " + e.getMessage());
        }
        return String.valueOf(jsonResponse);
    }

}