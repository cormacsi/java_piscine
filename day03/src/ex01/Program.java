package ex01;

public class Program {
    private static int count;

    private static volatile boolean isEgg = true;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid arguments!");
            System.exit(1);
        } else {
            parseArgs(args[0]);
        }

        Egg egg = new Egg(count);
        Thread eggThread = new Thread(egg);
        Hen hen = new Hen(count);
        Thread henThread = new Thread(hen);

        eggThread.start();
        henThread.start();
    }

    public static synchronized boolean isEgg() {
        return isEgg;
    }

    public static synchronized void setEgg(boolean egg) {
        isEgg = egg;
    }

    private static void parseArgs(String str) {
        if (str.matches("--[a-z]+=\\d+")) {
            String[] args = str.split("=");
            if (args[0].equals("--count"))
                count = Integer.parseInt(args[1]);
        }
    }
}

