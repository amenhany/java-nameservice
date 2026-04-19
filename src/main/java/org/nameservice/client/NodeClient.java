package org.nameservice.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NodeClient {

    private static final String HOST = "localhost";
    private static final int PORT = 3000; 

    public static void main(String[] args) {

        try (
            Socket socket = new Socket(HOST, PORT);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in))
        ) {

            System.out.println("Connected to NameService on port " + PORT);
            System.out.println("Commands:");
            System.out.println("REGISTER <name> <ip>");
            System.out.println("RESOLVE <name>");
            System.out.println("DEREGISTER <name>");
            System.out.println("Type EXIT to quit\n");

            String input;

            while (true) {
                System.out.print("> ");
                input = userInput.readLine();

                // exit condition
                if (input == null || input.equalsIgnoreCase("EXIT")) {
                    System.out.println("Disconnecting...");
                    break;
                }

                input = input.trim();

                // skip empty input
                if (input.isEmpty()) continue;

                // send to server
                out.println(input);

                // read response
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println(response);
            }

        } catch (IOException e) {
            System.out.println("ERROR: Could not connect to server\n" + e.getMessage());
        }
    }
}