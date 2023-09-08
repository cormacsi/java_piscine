package ex02;

public class MyThread implements Runnable {
    private int start;
    private int end;

    public MyThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += Program.getArray(i);
        }
        Program.addSum(sum);
        System.out.printf("%s: from %d to %d sum is %d\n", Thread.currentThread().getName(), start, end, sum);
    }
}
