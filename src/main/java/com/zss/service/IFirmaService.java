/*
package com.zss.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.zss.service.HmacService.generateHmacSha1;
import static com.zss.service.WebhookService.sendToWebhook;

@Service
public class IFirmaService {


    // Sprawdzanie limitu API
    public static void checkApiLimit(String userName, String symmetricalKeyAbonent) {
        String apiUrl = "https://www.ifirma.pl/iapi/abonent/limit.json";
        String keyNameAbonent = "abonent"; // Klucz autoryzacji: ABONENT

        try {
            // Przygotowanie hashMessage
            String requestContent = ""; // Puste dla żądania GET
            String baseUrl = apiUrl.split("\\?")[0]; // URL bez parametrów
            String hashMessage = baseUrl + userName + keyNameAbonent + requestContent;

            // Debug: Wyświetlenie hashMessage
            System.out.println("HashMessage: " + hashMessage);

            // Generowanie HMAC
            String hmacHash = generateHmacSha1(symmetricalKeyAbonent, hashMessage);

            // Budowa nagłówka Authentication
            String authHeader = String.format("IAPIS user=%s, hmac-sha1=%s", userName, hmacHash);

            // Debug: Wyświetlenie nagłówka Authentication
            System.out.println("Authentication Header: " + authHeader);

            // Tworzenie połączenia HTTP GET
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authentication", authHeader);
            connection.setRequestProperty("Accept", "application/json");

            // Obsługa odpowiedzi
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                StringBuilder jsonResponse = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResponse.append(line).append("\n");
                    }
                }
                // Wysłanie odpowiedzi do webhook
                sendToWebhook("https://n8n.melongroup.pl/webhook/java-app", jsonResponse.toString());
            } else {
                System.err.println("Błąd: Kod odpowiedzi HTTP " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Błąd podczas sprawdzania limitu API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/
