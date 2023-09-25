package edu.school21.sockets.server;

import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Server {

    private final UsersService usersService;

    private final MessageService messageService;

    private final List<ClientHandler> clientHandlers = new ArrayList<>();

    @Autowired
    public Server(UsersService usersService, MessageService messageService) {
        this.usersService = usersService;
        this.messageService = messageService;
    }

    public void start(Integer port) {
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(50000);

            while (!clientsDisconnected()) {
                try {
                    Socket clientSocket = server.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clientHandlers.add(clientHandler);
                    clientHandler.start();
                } catch (SocketTimeoutException e) {
                    System.out.println("Waiting for new users...");
                }
            }
        } catch (IOException e) {
            System.err.println("The server is not available!");
        }
    }

    private boolean clientsDisconnected() {
        return clientHandlers.size() > 0 && clientHandlers.stream().noneMatch(c -> c.active);
    }

    private synchronized void sendMessage(String text, String username) {
        messageService.saveMessage(text, username);
        String message = username + ": " + text;
        clientHandlers.stream()
                .filter(c -> c.signedIn)
                .forEach(c -> c.out.println(message));
    }

    class ClientHandler extends Thread {

        private final Socket socket;

        private String username;

        private boolean signedIn = false;

        private boolean active = true;

        private BufferedReader in;

        private PrintWriter out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Hello from Server!");

                menu();
                if (signedIn) {
                    chat();
                }

                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("Exception in thread :" + Thread.currentThread().getName());
            }
        }

        private void menu() {
            try {
                while (!signedIn) {
                    String line = in.readLine();
                    if (line.equalsIgnoreCase("signUp")) {
                        signUp();
                    } else if (line.equalsIgnoreCase("signIn")) {
                        signIn();
                    } else if (line.equalsIgnoreCase("exit")) {
                        out.println("You have left the chat.");
                        active = false;
                        break;
                    }
                    if (!signedIn) out.println("Please choose: signUp, signIn, Exit");
                }
            } catch (IOException e) {
                System.err.println("An error with reading the message");
            }
        }

        private void signUp() throws IOException {
            while (!signedIn) {
                out.println("Enter username:");
                username = in.readLine();
                out.println("Enter password:");
                if (usersService.signUp(username, in.readLine()) == null) {
                    out.println("The username is not available!");
                    out.println("1. Try again");
                    out.println("2. Go back");
                    if (in.readLine().equalsIgnoreCase("2")) {
                        break;
                    }
                } else {
                    signedIn = true;
                    out.println("Successful!");
                }
            }
        }

        private void signIn() throws IOException {
            while (!signedIn) {
                out.println("Enter username:");
                username = in.readLine();
                out.println("Enter password:");
                if (usersService.signIn(username, in.readLine())) {
                    out.println("Successful!");
                    signedIn = true;
                } else {
                    out.println("User does not exist or password is not valid");
                    out.println("1. Try again");
                    out.println("2. Go back");
                    if (in.readLine().equalsIgnoreCase("2")) {
                        break;
                    }
                }
            }
        }

        private void chat() throws IOException {
            out.println("Start messaging");
            while (true) {
                String message = in.readLine();
                if (message.trim().equalsIgnoreCase("exit")) {
                    out.println("You have left the chat.");
                    signedIn = false;
                    active = false;
                    break;
                }
                sendMessage(message, username);
            }
        }
    }

}