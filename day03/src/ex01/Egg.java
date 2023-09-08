package ex01;

public class Egg implements Runnable {
    private final int count;

    public Egg(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            while (!Program.isEgg()) {
                Thread.yield();
            }
            System.out.println("Egg");
            Program.setEgg(false);
        }
    }
}


//try {
//        Thread.sleep(0);
//        } catch (InterruptedException e) {
//        System.out.println("Not eggs turn!");
//        }