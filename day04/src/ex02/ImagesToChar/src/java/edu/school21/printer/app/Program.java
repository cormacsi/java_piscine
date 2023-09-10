package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.io.File;

@Parameters(separators = "=")
public class Program {
    @Parameter(names = "--white", description = "Replacement of white color")
    private static String white;
    @Parameter(names = "--black", description = "Replacement of black color")
    private static String black;
    private static File file = new File("src/resources/image.bmp");

    public static void main(String[] args) {
        Program main = new Program();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        Logic logic = new Logic(white, black);
        logic.readImage(file);
    }
}
