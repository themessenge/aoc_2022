package day6;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day6/input.txt");
        //File inputFile = new File("./src/day6/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        char[] currentLine = scanner.nextLine().toCharArray();
        scanner.close();
        int result1 = solve1(currentLine);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(currentLine);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(char[] input) {
        return calculateForLength(input, 4);
    }

    private static int solve2(char[] input) {
        return calculateForLength(input, 14);
    }

    public static int calculateForLength(char[] input, int length) {
        for (int i = 0; i < input.length - length; i++) {
            Set<Character> currSet = new HashSet<>();
            for (int j = i; j < i + length; j++) {
                currSet.add(input[j]);
                if (currSet.size() == length) {
                    return i + length;
                }
            }
        }
        return 0;
    }
}
