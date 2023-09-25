package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;

@Parameters(separators = "=")
public class Main {

    @Parameter(names = {"--server-port", "-sp"})
    private static Integer port;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You should parse one argument: --server-port=****");
            System.exit(1);
        }
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);

        Client client = new Client(port);
        client.start();
    }
}
