package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Writer extends Thread {

    private Socket clientSocket;

    public Writer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String line = in.readLine();
            while (!line.equalsIgnoreCase("You have left the chat.")) {
                System.out.println(line);
                line = in.readLine();
            }
            System.out.println(line);
        } catch (IOException e) {
            System.err.println("IOException in Client writer");
            System.exit(1);
        }
    }
}
