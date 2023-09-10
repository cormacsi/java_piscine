package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import java.io.File;
import java.util.List;

public class Program {
    private static char white;
    private static char black;
    private static File file = new File("src/resources/image.bmp");

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("There should be three arguments!");
        } else if (!parseArgs(args[0], args[1])) {
            throw new IllegalArgumentException("The arguments are invalid!");
        }

        List<String> list = Logic.readImage(file, white, black);
        for (String l : list) System.out.println(l);
    }
    private static boolean parseArgs(String a, String b) {
        if (a.length() == 1 && b.length() == 1) {
            white = a.charAt(0);
            black = b.charAt(0);
            return file.exists();
        }
        return false;
    }
}
