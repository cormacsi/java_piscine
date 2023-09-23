package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Integer port;

    public Client(Integer port) {
        this.port = port;
    }

    public void start() {
        try (Socket clientSocket = new Socket("localhost", port);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String message = in.readLine();
            System.out.println(message);
            while (!message.equalsIgnoreCase("Successful!")) {
                out.println(scanner.nextLine());
                message = in.readLine();
                System.out.println(message);
            }

        } catch (IOException e) {
            System.err.println("The server is not available!");
        }
    }
}
