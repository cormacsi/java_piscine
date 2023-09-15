package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        boolean result = true;
        if (number > 1) {
            for (int i = 2; i * i <= number && result; i++) {
                if (number % i == 0) {
                    result = false;
                }
            }
        } else {
            throw new IllegalNumberException("The number is less than 0 or zero or one");
        }
        return result;
    }

    public int digitsSum(int number) {
        int result = 0;
        while (number != 0) {
            result += number % 10;
            number /= 10;
        }
        return result > 0 ? result : -result;
    }
}
