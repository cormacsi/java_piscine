package ex00;

public class Program {
    private static int count;

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
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.err.println("Interrupted!");
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }

    private static void parseArgs(String str) {
        if (str.matches("--[a-z]+=\\d+")) {
            String[] args = str.split("=");
            if (args[0].equals("--count"))
                count = Integer.parseInt(args[1]);
        }
    }
}
