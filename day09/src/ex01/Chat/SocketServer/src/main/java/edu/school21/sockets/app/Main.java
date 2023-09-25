package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


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

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = ctx.getBean("server", Server.class);
        server.start(port);
    }
}
