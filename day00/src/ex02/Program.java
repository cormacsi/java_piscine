package ex02;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        int next = 0, result = 0;
        Scanner sc = new Scanner(System.in);
        while (next != 42) {
            next = sc.nextInt();
            if (next > 1 && isPrimeNumber(digitSum(next))) result++;
        }
        System.out.println(result);
        sc.close();
    }
    private static boolean isPrimeNumber(int a) {
        boolean isPrime = true;
        int i = 2;
        while (i*i <= a) {
            if (a % i == 0) {
                isPrime = false;
                break;
            }
            i++;
        }
        return isPrime;
    }
    private static int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += (num % 10);
            num /= 10;
        }
        return sum;
    }
}
