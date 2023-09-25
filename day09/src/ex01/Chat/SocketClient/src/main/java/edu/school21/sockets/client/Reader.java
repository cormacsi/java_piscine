package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Reader extends Thread {

    private Socket clientSocket;

    public Reader(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            while (hasNextLine()) {
                out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("IOException in Client reader");
            System.exit(1);
        }
    }

    private boolean hasNextLine() throws IOException {
        while (System.in.available() == 0) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }
}
