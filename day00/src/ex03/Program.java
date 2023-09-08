package ex03;

import java.util.Objects;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        int currentWeek = 0;
        Scanner scan = new Scanner(System.in);
        String week = scan.nextLine();
        while (!week.equals("42") && currentWeek != 18) {
            currentWeek++;
            if (Integer.parseInt(week.substring(5)) != currentWeek) {
                exitProgram();
            }
            int min = 9;
            for (int i = 0; i < 5; i++) {
                int value = scan.nextInt();
                if (value < 1 || value > 9) exitProgram();
                if (min > value) min = value;
            }
            printWeek(week, min);
            week = scan.nextLine();
            week = scan.nextLine();
        }
        scan.close();
    }
    private static void exitProgram() {
        System.out.println("IllegalArgument");
        System.exit(-1);
    }

    private static void printWeek(String week, int min) {
        System.out.print(week + " ");
        for (int i = 0; i < min; i++) {
            System.out.print("=");
        }
        System.out.println(">");
    }
}
