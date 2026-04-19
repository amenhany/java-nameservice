package org.nameservice.server;

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

    }
}
