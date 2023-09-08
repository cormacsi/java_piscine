package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int prime = scan.nextInt();
        if (prime < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        } else {
            System.out.println(isPrimeNumber(prime));
        }
        scan.close();
    }
    private static String isPrimeNumber(int a) {
        boolean isPrime = true;
        int i = 2;
        while (i*i <= a) {
            if (a % i == 0) {
                isPrime = false;
                break;
            }
            i++;
        }
        return String.format("%b %d", isPrime, i - 1);
    }

}