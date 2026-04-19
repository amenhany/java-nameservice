package org.nameservice.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NameServiceServer {
    public static final int DEFAULT_PORT = 3000;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        ServiceRegistry registry = new ServiceRegistry();

        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server is running on Port " + port);

            while (true) {
                Socket client = ss.accept();
                new Thread(new ClientHandler(client, registry)).start();
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: Could not create the server socket\n" + e.getMessage());
        }
    }
}
