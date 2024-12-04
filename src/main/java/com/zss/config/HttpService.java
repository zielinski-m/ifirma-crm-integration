/*
package com.zss.config;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {

    private String authHeader;
    private String apiUrlPaid;

    public HttpURLConnection createConnection() throws Exception {
        URL url = new URL(apiUrlPaid); // Dodanie filtra opłaconych faktur
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authentication", authHeader);
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }


    public String getResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line).append("\n");
            }
            return jsonResponse.toString();
        }
    }
}
*/
