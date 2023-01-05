package day25;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day25/input.txt");
        //File inputFile = new File("./src/day25/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> input = new ArrayList<>();
        while (scanner.hasNext()) {
            input.add(scanner.nextLine());
        }
        scanner.close();
        long result1 = solve1(input);
        System.out.println("Result 1:" + result1 + " - " + snafuEncoder(result1));
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static long solve1(List<String> input) {
        long sum = 0;
        for (String s : input) {
            sum += snafuDecoder(s);
        }
        return sum;
    }

    private static int solve2(List<String> input) {
        return input.size();
    }


    private static long snafuDecoder(String s) {
        //System.out.print(s + " - ");
        long currentNumber = 0;
        char[] snafu = new StringBuilder().append(s).reverse().toString().toCharArray();
        int potenz = 0;
        for (char c : snafu) {
            double pow = Math.pow(5, potenz);
            currentNumber += pow * snafuChars(c);
            potenz += 1;
        }
        //System.out.println(currentNumber);
        return currentNumber;
    }

    private static int snafuChars(char c) {
        switch (c) {
            case '2':
                return 2;
            case '1':
                return 1;
            case '0':
                return 0;
            case '-':
                return -1;
            case '=':
                return -2;
        }
        System.out.println("Error");
        return 5;
    }

    private static String snafuEncoder(long number) {
        final char[] SNAFUDigits = {'0', '1', '2', '=', '-'};
        if (number == 0) {
            return "";
        }
        final int remainder = (int) (number % 5);
        final char digit = SNAFUDigits[remainder];
        return snafuEncoder((number + 2) / 5) +
            digit;

    }

}
