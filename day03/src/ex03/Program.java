package ex03;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private static int threadsCount;
    private static int filesNum = 0;
    private static int nextNum = 0;
    private static List<String> filesList = new ArrayList<>();
    private static List<Thread> threadList = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        } else if (parseArgs(args[0])) {
            System.err.println("Invalid arguments!");
            System.exit(2);
        }

        try (FileReader reader = new FileReader("files_urls.txt");
            BufferedReader bufReader = new BufferedReader(reader)) {
            boolean err = false;
            while (bufReader.ready() && !err) {
                err = parseLine(bufReader.readLine());
            }
        } catch (IOException e) {
            System.err.println("Error with reading the file!");
            System.exit(3);
        }
        System.out.println("FilesNum: " + filesNum);
        for (String f : filesList) {
            System.out.println(f);
        }
        System.out.println("Threads: " + threadsCount);
//        if (filesNum > 0 && threadsCount < filesNum)
            createThreads();
    }

    public static String getFilename(int num) {
        return filesList.get(num - 1);
    }

    public static synchronized int getNextNum() {
        nextNum++;
        return nextNum <= filesNum ? nextNum : 0;
    }

    private static void createThreads() {
        for (int i = 1; i <= threadsCount; i++) {
            Thread tmp = new Thread(new MyThread());
            tmp.setName("Thread-" + i);
            System.out.println(tmp.getName());
            threadList.add(tmp);
        }
        for (Thread t : threadList) {
            t.start();
        }
    }

    private static boolean parseLine(String str) {
        if (str.matches("^\\d+ https?://[\\w\\.\\/\\-]+$")) {
            String[] args = str.split(" ");
            if (args.length == 2 && filesNum + 1 == Integer.parseInt(args[0])) {
                filesList.add(args[1]);
                filesNum++;
                return false;
            }
        }
        System.err.printf("Error with reading the file № %d!\n", filesNum + 1);
        return true;
    }

    private static boolean parseArgs(String str) {
        if (str.matches("--[a-zA-Z]+=\\d+")) {
            String[] args = str.split("=");
            if (args[0].equals("--threadsCount")) {
                threadsCount = Integer.parseInt(args[1]);
                return false;
            }
        }
        return true;
    }
}
