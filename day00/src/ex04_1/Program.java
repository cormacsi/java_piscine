package ex04_1;

import java.util.*;

public class Program {
    private static final Map<Character, Integer> map = new HashMap<>();
    private static final int[] topNum = new int[10];
    private static final char[] topChar = new char[10];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        createMap(scan.nextLine());
        findTen();
        setTable();
        scan.close();
    }

    private static void createMap(String input) {
        int length = input.length();
        if (length > 1000) System.exit(-1);
        char[] letters = input.toCharArray();
        for (char letter : letters) {
            if (map.containsKey(letter)) {
                Integer value = map.get(letter);
                map.put(letter, ++value);
            } else {
                map.put(letter, 1);
            }
        }
    }

    private static void findTen() {
        char maxSymbol = 'a';
        int i = 0;
        Set<Character> allKeys = map.keySet();
        while (!allKeys.isEmpty() && i < 10) {
            int maxNumber = 0;
            for (char c : allKeys) {
                int tmp = map.get(c);
                if (tmp > maxNumber) {
                    maxNumber = tmp;
                    maxSymbol = c;
                } else if (tmp == maxNumber && c < maxSymbol) {
                    maxSymbol = c;
                }
            }
            topChar[i] = maxSymbol;
            topNum[i++] = maxNumber;
            allKeys.remove(maxSymbol);
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