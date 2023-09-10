package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import java.io.File;
import java.util.List;

public class Program {
    private static char white;
    private static char black;
    private static File file;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("There should be three arguments!");
        } else if (!parseArgs(args[0], args[1], args[2])) {
            throw new IllegalArgumentException("The arguments are invalid!");
        }

        List<String> list = Logic.readImage(file, white, black);
        for (String l : list) System.out.println(l);
    }

    private static boolean parseArgs(String a, String b, String c) {
        if (a.length() == 1 && b.length() == 1 && c.matches("^\\S+.bmp$")) {
            white = a.charAt(0);
            black = b.charAt(0);
            file = new File(c);
            return file.exists();
        }
        return false;
    }
}
