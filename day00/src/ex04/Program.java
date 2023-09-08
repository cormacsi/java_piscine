package ex04;

import java.util.Arrays;
import java.util.Scanner;

public class Program {
    private static final int[] topNum = new int[10];
    private static final char[] topChar = new char[10];
    private static int[] numbers;
    private static char[] unLetters;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        countLetters(scan.nextLine());
        findTen();
        setTable();
        scan.close();
    }

    private static void countLetters(String input) {
        int length = input.length();
        if (length > 1000) System.exit(-1);
        char[] letters = input.toCharArray();
        numbers = new int[length];
        unLetters = new char[length];
        int counter = 0;
        for (char letter : letters) {
            boolean found = false;
            for (int j = 0; j < counter; j++) {
                if (unLetters[j] == letter) {
                    numbers[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                unLetters[counter] = letter;
                numbers[counter] = 1;
                counter++;
            }
        }
        numbers = Arrays.copyOf(numbers, counter);
        unLetters = Arrays.copyOf(unLetters, counter);
    }

    private static boolean checkTop(char current, int indMax) {
        boolean result = true;
        for (int i = 0; i < indMax; i++) {
            if (topChar[i] == current) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static void findTen() {
        for (int i = 0; i < topNum.length; i++) {
            int max = 0;
            int index = 0;
            for (int j = 0; j < numbers.length; j++) {
                if (checkTop(unLetters[j], i)) {
                    if (max < numbers[j]) {
                        max = numbers[j];
                        index = j;
                    } else if (max == numbers[j] && (unLetters[j] < unLetters[index])) {
                        max = numbers[j];
                        index = j;
                    }
                }
            }
            if (max == 0) break;
            topNum[i] = max;
            topChar[i] = unLetters[index];
        }
    }

    private static int getLength() {
        int length = 10;
        for (int i = 0; i < 10; i++) {
            if (topNum[i] == 0) {
                length = i;
                break;
            }
        }
        return length;
    }

    private static void setTable() {
        int width = getLength();
        char[][] table = new char[12][width];
        for (int i = 0; i < width; i++) {
            int start = 10 - (topNum[i] * 10 / topNum[0]);
            for (int j = 0; j < 12; j++) {
                if (start == j) table[j][i] = 'N';
                else if (j == 11) table[j][i] = topChar[i];
                else if (start < j) table[j][i] = '#';
                else table[j][i] = ' ';
            }
        }
        printTable(table, width);
    }

    private static void printTable(char[][] table, int width) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < width; j++) {
                if (table[i][j] == ' ') break;
                else if (table[i][j] == 'N') System.out.printf("%3d", topNum[j]);
                else System.out.printf("%3c", table[i][j]);
                if (j != width - 1 && table[i][j + 1] != ' ') System.out.print(" ");
            }
            System.out.println();
        }
    }
}
