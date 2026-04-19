package org.nameservice.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {
    Socket client;
    ServiceRegistry registry;

    ClientHandler(Socket client, ServiceRegistry registry) {
        this.client = client;
        this.registry = registry;
    }

    @Override
    public void run() {
        // try-with-resources ensures streams and socket are closed automatically
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true) // true for auto-flush
        ) {
            String inputLine;
            // Read lines until client disconnects
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                
                String[] parts = inputLine.split("\\s+");
                if (parts.length == 0 || parts[0].isEmpty()) continue;

                CommandType command = CommandType.fromString(parts[0]);
                String response;
                 if (command == null) {
                    response = "ERROR: Invalid Command";
                    out.println(response);
                    continue;
                }

                try {
                    switch (command) {
                        case REGISTER:
                            if (parts.length >= 3) {
                                response = registry.register(parts[1], parts[2]);
                            } else {
                                response = "ERROR: Missing arguments for REGISTER";
                            }
                            break;
                        case RESOLVE:
                            if (parts.length >= 2) {
                                response = registry.resolve(parts[1]);
                            } else {
                                response = "ERROR: Missing arguments for RESOLVE";
                            }
                            break;
                        case DEREGISTER:
                            if (parts.length >= 2) {
                                response = registry.deregister(parts[1]);
                            } else {
                                response = "ERROR: Missing arguments for DEREGISTER";
                            }
                            break;
                       default:
                            response = "ERROR: Unknown Command";
                    }
                } catch (Exception e) {
                   response = "ERROR: Server Exception - " + e.getMessage();
                }

                System.out.println("Sending to client: " + response);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected or error occurred: " + e.getMessage());
        } finally {
            try {
                if (client != null && !client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to close socket: " + e.getMessage());
            }
        }
    }
}
