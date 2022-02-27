package com.revature.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PrismService {
    public static void main(String[] args) {

        try {

            String rootUrl = "http://localhost:5000/prism";

            // What endpoint are you wanting to talk to?
            URL prismRegisterUrl = new URL(rootUrl + "/organizations");

            // Create a connection to send the request over (and to receive the response)
            HttpURLConnection registerConnection = (HttpURLConnection) prismRegisterUrl.openConnection();

            // Set the HTTP method you wish to use for this request
            registerConnection.setRequestMethod("POST");

            // details about what we are sending in this request
            registerConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // details about what content type(s) we will accept in the response
            registerConnection.setRequestProperty("Accept", "application/json");

            // To send a request body, enable doOutput (not necessary if no request body is being sent)
            registerConnection.setDoOutput(true);

            // Define the data we want to send
            String newOrgRegistrationRequestPayload = "{\"name\": \"Test-Org\", \"key\": \"super-secret-key\"}";

            // Attach the payload data to the request and send it
            try (OutputStream os = registerConnection.getOutputStream()) {
                byte[] payloadBytes = newOrgRegistrationRequestPayload.getBytes(StandardCharsets.UTF_8);
                os.write(payloadBytes);
            }

            /// Read the response from the connection's input stream
            try (BufferedReader br = new BufferedReader(new InputStreamReader(registerConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                // TODO parse this JSON into something useful, maybe.
                // After parsing by hand, you've earned the right to use Jackson: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.13.1
                System.out.println(response);

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
