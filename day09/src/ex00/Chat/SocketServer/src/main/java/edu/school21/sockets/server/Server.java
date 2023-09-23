package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final Integer port;

    private final UsersService usersService;

    private BufferedReader in;

    private PrintWriter out;

    public Server(Integer port) {
        this.port = port;
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        usersService = ctx.getBean("usersServiceImpl", UsersService.class);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port);
             Socket clientSocket = server.accept()) {

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Hello from Server!");

            menu();

            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("The server is not available!");
        }
    }

    private void menu() {
        boolean stop = false;
        try {
            while (!stop) {
                if (in.readLine().equalsIgnoreCase("signUp")) {
                    stop = true;
                    signUp();
                } else {
                    out.println("Please signUp");
                }
            }
        } catch (IOException e) {
            System.err.println("An error with reading the message");
        }
    }

    private void signUp() throws IOException {
        boolean successful = false;
        String enter = "Enter username:";
        while (!successful) {
            out.println(enter);
            String username = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();
            successful = usersService.signUp(username, password);
            if (!successful) {
                enter = "The username is not available! Enter another username:";
            }
        }
        out.println("Successful!");
    }
}
