package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    static NumberWorker numberWorker;

    @BeforeAll
    public static void init() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 43, 47, 71, 73, 79, 83, 89, 97, 103, 137, 163, 223})
    void isPrimeForPrimes(int argument) {
        Assertions.assertTrue(numberWorker.isPrime(argument));
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(ints = {4, 6, 9, 16, 18, 26, 27, 28, 33, 42, 54, 57, 63, 74, 88, 100, 117, 159, 171, 201, 203, 207})
    void isPrimeForNotPrimes(int argument) {
        Assertions.assertFalse(numberWorker.isPrime(argument));
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(ints = {0, 1, -1, -2, -4, -7, -9, -14, -17, -23, -45, -143, -246, -436, -766, -432516})
    void isPrimeForIncorrectNumbers(int argument) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(argument));
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 0, useHeadersInDisplayName = true)
    void digitsSum(int argument, int sum) {
        Assertions.assertEquals(sum, numberWorker.digitsSum(argument));
    }
}
