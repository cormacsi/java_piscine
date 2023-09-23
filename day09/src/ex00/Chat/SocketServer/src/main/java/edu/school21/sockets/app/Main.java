package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.server.Server;

@Parameters(separators = "=")
public class Main {

    @Parameter(names = {"--port", "-p"})
    private static Integer port;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You should parse one argument: --port=****");
            System.exit(1);
        }
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);

        Server server = new Server(port);
        server.start();
    }
}
