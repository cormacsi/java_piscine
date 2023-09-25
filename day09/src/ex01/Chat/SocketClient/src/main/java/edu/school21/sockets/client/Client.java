package edu.school21.sockets.client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private final Integer port;

    public Client(Integer port) {
        this.port = port;
    }

    public void start() {
        try (Socket clientSocket = new Socket("localhost", port)) {
            Writer writer = new Writer(clientSocket);
            Reader reader = new Reader(clientSocket);

            writer.start();
            reader.start();

            writer.join();
            reader.interrupt();
        } catch (IOException e) {
            System.err.println("The server is not available!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
