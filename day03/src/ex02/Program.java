package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {
    private static int arraySize;
    private static int threadsCount;
    private static int[] array;
    private static List<Thread> threadList;
    private static long sum;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        } else if (parseArgs(args[0]) || parseArgs(args[1])) {
            System.err.println("Invalid arguments!");
            System.exit(2);
        }
        createArray();
        System.out.println("Sum: " + standSum());
        createThreads();
        System.out.println("Sum by threads: " + sum);
    }

    public static synchronized void addSum(long sum) {
        Program.sum += sum;
    }

    public static int getArray(int i) {
        return array[i];
    }

    public static long standSum() {
        long sum = 0;
        for (int i = 0; i < arraySize; i++) {
            sum += array[i];
        }
        return sum;
    }
    private static void createArray() {
        array = new int[arraySize];
        Random rand = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = rand.nextInt(-1000, 1000);
        }
    }
    private static void createThreads() {
        threadList = new ArrayList<>();
        int step = findStep();
        for (int i = 1, j = 0; i <= threadsCount; i++) {
            int next = Math.min(j + step, arraySize);
            Thread tmp = new Thread(new MyThread(j, next));
            tmp.setName("Thread " + i);
            threadList.add(tmp);
            j += step;
        }
        for (Thread t : threadList) {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }
        }
    }

    private static int findStep() {
        double mid = (double) arraySize / threadsCount;
        int step = (int) mid;
        if (mid % 1 > 0.3) step += 1;
        return step;
    }

    private static boolean parseArgs(String str) {
        boolean error = true;
        if (str.matches("--[a-zA-Z]+=\\d+")) {
            String[] args = str.split("=");
            if (args[0].equals("--arraySize")) {
                arraySize = Integer.parseInt(args[1]);
                error = arraySize <= 0 || arraySize >= 2000000;
            } else if (args[0].equals("--threadsCount")) {
                threadsCount = Integer.parseInt(args[1]);
                error = threadsCount > arraySize;
            }
        }
        return error;
    }
}
